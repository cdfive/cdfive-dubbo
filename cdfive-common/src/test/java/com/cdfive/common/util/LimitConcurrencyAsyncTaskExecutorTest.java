package com.cdfive.common.util;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
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
        long start = System.currentTimeMillis();
        int concurrency = 5;
        int total = 1000;
        CountDownLatch latch = new CountDownLatch(total);

        LimitConcurrencyAsyncTaskExecutor limitConcurrencyAsyncTaskExecutor = new LimitConcurrencyAsyncTaskExecutor(concurrency);

        List<String> codes = IntStream.range(1, 1 + total).mapToObj(i -> String.valueOf(i)).collect(Collectors.toList());

        LimitConcurrencyAsyncTaskExecutor.AsyncTaskExecutor<String> asyncTaskExecutor = new LimitConcurrencyAsyncTaskExecutor.AsyncTaskExecutor<String>() {
            @Override
            public void executeTask(String code, Runnable callback, LimitConcurrencyAsyncTaskExecutor.Context context) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(180, 200));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.err.println(limitConcurrencyAsyncTaskExecutor.getTraceId() + "," + Thread.currentThread().getName() + "=>code=" + code + ",context=" + context);

                        callback.run();

                        latch.countDown();
                    }
                }).start();
            }
        };

        limitConcurrencyAsyncTaskExecutor.executeTasks(codes, asyncTaskExecutor);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // less than 40s
        System.out.println("total done,cost=" + (System.currentTimeMillis() - start) + "ms");
    }
}
