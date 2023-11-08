package com.cdfive.common.util;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author cdfive
 */
public class LimitBatchAsyncTaskExecutorTest {

    @Test
    public void testCase1() {
        int batch = 5;

        LimitBatchAsyncTaskExecutor limitBatchAsyncTaskExecutor = new LimitBatchAsyncTaskExecutor(batch);

        List<String> codes = IntStream.range(1, 1 + 100).mapToObj(i -> String.valueOf(i)).collect(Collectors.toList());

        LimitBatchAsyncTaskExecutor.AsyncTaskExecutor<String> asyncTaskExecutor = new LimitBatchAsyncTaskExecutor.AsyncTaskExecutor<String>() {
            @Override
            public void executeTask(String code, Runnable callback, LimitBatchAsyncTaskExecutor.Context context) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(200));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.err.println(limitBatchAsyncTaskExecutor.getTraceId() + "," + Thread.currentThread().getName() + "=>code=" + code + ",context=" + context);

                        callback.run();
                    }
                }).start();
            }
        };

        limitBatchAsyncTaskExecutor.executeTasks(codes, asyncTaskExecutor);
    }
}
