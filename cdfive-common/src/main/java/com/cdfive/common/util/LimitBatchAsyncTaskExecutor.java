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
import java.util.concurrent.CountDownLatch;

/**
 * 限制批处理的异步任务执行器
 * <p>
 * 1. 任务本身需是异步执行的
 * 2. 此执行器控制异步执行并发数, 避免大量并发导致系统负载过大
 *
 * @author cdfive
 */
@Slf4j
public class LimitBatchAsyncTaskExecutor {

    // 每批次处理数
    private int batch;

    // 跟踪id
    private String traceId;

    public LimitBatchAsyncTaskExecutor(int batch) {
        this(batch, CommonUtil.getTraceId());
    }

    public LimitBatchAsyncTaskExecutor(int batch, String traceId) {
        Assert.isTrue(batch > 0, "batch must be greater than 0");
        this.batch = batch;
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

        log.info(traceId + ",LimitBatchAsyncTaskExecutor executeTasks start,total={},batch={}", total, batch);
        long totalStart = System.currentTimeMillis();
        long batchStart = System.currentTimeMillis();

        CountDownLatch latch = null;
        Runnable callback = null;
        int index = 0;
        int batchIndex = 0;
        int batchTotal = (total / batch) + (total % batch == 0 ? 0 : 1);
        while (tasks.hasNext()) {
            T task = tasks.next();
            index++;
            if (latch == null) {
                batchStart = System.currentTimeMillis();
                batchIndex++;
                latch = new CountDownLatch((batchIndex < batchTotal) ? batch : (total - index + 1));
                CountDownLatch finalLatch = latch;
                callback = new Runnable() {
                    @Override
                    public void run() {
                        finalLatch.countDown();
                    }
                };
            }

            Context context = new Context(index, total, batchIndex, batchTotal);
            log.info(traceId + ",LimitBatchAsyncTaskExecutor executeTask start,batch={},context={}", batch, context);
            long start = System.currentTimeMillis();
            Runnable finalCallback = callback;
            asyncTaskExecutor.executeTask(task, () -> {
                log.info(traceId + ",LimitBatchAsyncTaskExecutor executeTask done,cost={}ms,batch={},context={}", (System.currentTimeMillis() - start), batch, context);
                finalCallback.run();
            }, context);

            if (index % batch == 0 || index == total) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    log.error(traceId + ",LimitBatchAsyncTaskExecutor await error", e);
                }

                latch = null;
                callback = null;
                log.info(traceId + ",LimitBatchAsyncTaskExecutor batch done,cost={}ms,index=({}/{}),batchIndex=({}/{})"
                        , (System.currentTimeMillis() - batchStart), index, total, batchIndex, batchTotal);
            }
        }

        log.info(traceId + ",LimitBatchAsyncTaskExecutor executeTasks success,total cost={}ms,batch={}", (System.currentTimeMillis() - totalStart), batch);
    }

    public int getBatch() {
        return batch;
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

        // 批量下标
        private int batchIndex;

        // 批量总数
        private int batchTotal;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }
    }

    @Override
    public String toString() {
        return "LimitBatchAsyncTaskExecutor{" +
                "batch=" + batch +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}

