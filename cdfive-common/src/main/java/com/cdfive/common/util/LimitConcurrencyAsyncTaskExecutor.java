package com.cdfive.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

/**
 * 限制并发度的异步任务执行器
 * <p>
 * 1. 任务本身需是异步执行的
 * 2. 此执行器控制异步执行并发数, 避免大量并发导致系统负载过大
 *
 * @author cdfive
 */
@Slf4j
public class LimitConcurrencyAsyncTaskExecutor {

    // 并发数
    private int concurrency;

    // 跟踪id
    private String traceId;

    public LimitConcurrencyAsyncTaskExecutor(int concurrency) {
        this(concurrency, CommonUtil.getTraceId());
    }

    public LimitConcurrencyAsyncTaskExecutor(int concurrency, String traceId) {
        Assert.isTrue(concurrency > 0, "concurrency must be greater than 0");
        this.concurrency = concurrency;
        this.traceId = traceId;
    }

    public <T> void executeTasks(Collection<T> tasks, AsyncTaskExecutor<T> asyncTaskExecutor) {
        Assert.isTrue(tasks != null && tasks.size() > 0, "tasks can't be empty");
        this.executeTasks(tasks.iterator(), tasks.size(), asyncTaskExecutor);
    }

    public <T> void executeTasks(Iterator<T> tasks, int total, AsyncTaskExecutor<T> asyncTaskExecutor) {
        Assert.notNull(tasks, "tasks can't be null");
        Assert.isTrue(total > 0, "total must be greater than 0");
        Assert.notNull(asyncTaskExecutor, "asyncTaskExecutor can't be null");

        log.info(traceId + ",LimitConcurrencyAsyncTaskExecutor executeTasks start,total={},concurrency={}", total, concurrency);
        long totalStart = System.currentTimeMillis();
        long batchStart = System.currentTimeMillis();

        Semaphore semaphore = new Semaphore(concurrency);
        Runnable callback = new Runnable() {
            @Override
            public void run() {
                semaphore.release();
            }
        };
        int index = 0;
        while (tasks.hasNext()) {
            T task = tasks.next();
            index++;

            semaphore.acquireUninterruptibly();
            Context context = new Context(index, total);
            log.info(traceId + ",LimitConcurrencyAsyncTaskExecutor executeTask start,concurrency={},context={}", concurrency, context);
            long start = System.currentTimeMillis();

            asyncTaskExecutor.executeTask(task, () -> {
                log.info(traceId + ",LimitConcurrencyAsyncTaskExecutor executeTask done,cost={}ms,concurrency={},context={}", (System.currentTimeMillis() - start), concurrency, context);
                callback.run();
            }, context);
        }

        log.info(traceId + ",LimitConcurrencyAsyncTaskExecutor executeTasks success,total cost={}ms,concurrency={}", (System.currentTimeMillis() - totalStart), concurrency);
    }

    public int getConcurrency() {
        return concurrency;
    }

    public String getTraceId() {
        return traceId;
    }

    /**
     * 异步任务执行器
     */
    public static interface AsyncTaskExecutor<T> {

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

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }
    }

    @Override
    public String toString() {
        return "LimitConcurrencyAsyncTaskExecutor{" +
                "concurrency=" + concurrency +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}

