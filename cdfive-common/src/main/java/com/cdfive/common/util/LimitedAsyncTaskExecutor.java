package com.cdfive.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

/**
 * 受限的异步任务执行器
 * <p>
 * 1. 任务本身需是异步执行的
 * 2. 此执行器控制异步执行并发数, 避免大量并发导致系统负载过大
 *
 * @author cdfive
 */
@Slf4j
public class LimitedAsyncTaskExecutor {

    // 并发数
    private int concurrency;

    // 跟踪id
    private String traceId;

    public LimitedAsyncTaskExecutor(int concurrency) {
        this(concurrency, CommonUtil.getTraceId());
    }

    public LimitedAsyncTaskExecutor(int concurrency, String traceId) {
        Assert.isTrue(concurrency > 0, "concurrency must be greater than 0");
        this.concurrency = concurrency;
        this.traceId = traceId;
    }

    public <T> void executeTasks(Collection<T> tasks, TaskExecutor<T> taskExecutor) {
        Assert.isTrue(tasks != null && tasks.size() > 0, "tasks can't be empty");
        this.executeTasks(tasks.iterator(), tasks.size(), taskExecutor);
    }

    public <T> void executeTasks(Iterator<T> tasks, int total, TaskExecutor<T> taskExecutor) {
        Assert.notNull(tasks, "tasks can't be null");
        Assert.isTrue(total > 0, "total must be greater than 0");
        Assert.notNull(taskExecutor, "taskExecutor can't be null");

        log.info(traceId + ",LimitedAsyncTaskExecutor executeTasks start,total={},concurrency={}", total, concurrency);
        long totalStart = System.currentTimeMillis();
        long batchStart = System.currentTimeMillis();

        CountDownLatch latch = null;
        Runnable callback = null;
        int index = 0;
        int batchIndex = 0;
        int batchTotal = (total / concurrency) + (total % concurrency == 0 ? 0 : 1);
        while (tasks.hasNext()) {
            T task = tasks.next();
            index++;
            if (latch == null) {
                batchStart = System.currentTimeMillis();
                batchIndex++;
                latch = new CountDownLatch((batchIndex < batchTotal) ? concurrency : (total - index + 1));
                CountDownLatch finalLatch = latch;
                callback = new Runnable() {
                    @Override
                    public void run() {
                        finalLatch.countDown();
                    }
                };
            }

            Context context = new Context(index, total, batchIndex, batchTotal);
            log.info(traceId + ",LimitedAsyncTaskExecutor executeTask start,concurrency={},context={}", concurrency, context);
            taskExecutor.executeTask(task, callback, context);

            if (index % concurrency == 0 || index == total) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    log.error(traceId + ",LimitedAsyncTaskExecutor await error", e);
                }

                latch = null;
                callback = null;
                log.info(traceId + ",LimitedAsyncTaskExecutor batch done,cost={}ms,index=({}/{}),batch=({}/{})"
                        , (System.currentTimeMillis() - batchStart), index, total, batchIndex, batchTotal);
            }
        }

        log.info(traceId + ",LimitedAsyncTaskExecutor executeTasks success,total cost={}ms,concurrency={}", (System.currentTimeMillis() - totalStart), concurrency);
    }

    public int getConcurrency() {
        return concurrency;
    }

    public String getTraceId() {
        return traceId;
    }

    /**
     * 任务执行器
     */
    public static interface TaskExecutor<T> {

        /**
         * 执行任务,任务本身需是异步执行的
         */
        void executeTask(T task, Runnable callback, Context context);
    }

    /**
     * 上下文
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Context implements Serializable {

        private static final long serialVersionUID = 2916376548269524746L;

        // 下标
        private int index;

        // 总数
        private int total;

        // 批量下标
        private int batchIndex;

        // 批量总数
        private int batchTotal;
    }

    @Override
    public String toString() {
        return "LimitedAsyncTaskExecutor{" +
                "concurrency=" + concurrency +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}

