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
public class LimitConcurrencyAsyncTaskExecutorTest {

    @Test
    public void testCase1() {
        int concurrency = 5;

        LimitConcurrencyAsyncTaskExecutor limitConcurrencyAsyncTaskExecutor = new LimitConcurrencyAsyncTaskExecutor(concurrency);

        List<String> codes = IntStream.range(1, 1 + 100).mapToObj(i -> String.valueOf(i)).collect(Collectors.toList());

        LimitConcurrencyAsyncTaskExecutor.AsyncTaskExecutor<String> asyncTaskExecutor = new LimitConcurrencyAsyncTaskExecutor.AsyncTaskExecutor<String>() {
            @Override
            public void executeTask(String code, Runnable callback, LimitConcurrencyAsyncTaskExecutor.Context context) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(200));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.err.println(limitConcurrencyAsyncTaskExecutor.getTraceId() + "," + Thread.currentThread().getName() + "=>code=" + code + ",context=" + context);

                        callback.run();
                    }
                }).start();
            }
        };

        limitConcurrencyAsyncTaskExecutor.executeTasks(codes, asyncTaskExecutor);

        try {
            // Important, main thread wait 3 second,
            // otherwise some threads may not be executed since sub thread exit with main thread
            TimeUnit.MILLISECONDS.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
