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
 * 异步任务执行器
 *
 * @author xiejihan
 * @date 2022-05-18
 */
@Slf4j
public class AsyncTaskExecutor {

    private int concurrency;

    private String traceId;

    public AsyncTaskExecutor(int concurrency) {
        this(concurrency, CommonUtil.getTraceId());
    }

    public AsyncTaskExecutor(int concurrency, String traceId) {
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

        log.info(traceId + ",AsyncTaskExecutor executeTasks start,total={},concurrency={}", total, concurrency);
        long totalStart = System.currentTimeMillis();

        CountDownLatch latch = null;
        Runnable callback = null;
        int index = 0;
        int batchIndex = 0;
        int batchTotal = (total / concurrency) + (total % concurrency == 0 ? 0 : 1);
        while (tasks.hasNext()) {
            T task = tasks.next();
            index++;
            if (latch == null) {
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
            taskExecutor.executeTask(task, callback, context);

            if (index % concurrency == 0 || index == total) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    log.error(traceId + ",AsyncTaskExecutor await error", e);
                }

                latch = null;
                callback = null;
            }
        }

        log.info(traceId + ",AsyncTaskExecutor executeTasks success,total cost={}ms,concurrency={}", (System.currentTimeMillis() - totalStart), concurrency);
    }

    public int getConcurrency() {
        return concurrency;
    }

    public String getTraceId() {
        return traceId;
    }

    public static interface TaskExecutor<T> {

        void executeTask(T task, Runnable callback, Context context);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Context implements Serializable {

        private static final long serialVersionUID = 2916376548269524746L;

        private int index;

        private int total;

        private int batchIndex;

        private int batchTotal;
    }
}

