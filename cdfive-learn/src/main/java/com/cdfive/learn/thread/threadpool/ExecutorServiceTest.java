package com.cdfive.learn.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class ExecutorServiceTest {

    public static void main(String[] args) {
        int threadSize = 5;
        ExecutorService es = Executors.newFixedThreadPool(threadSize);

        int taskTotal = 10;
        for (int i = 0; i < taskTotal; i++) {
            es.execute(() -> {
                long start = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + " start");

                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " end,cost=" + (System.currentTimeMillis() - start) + "ms");
            });
        }

        // Recommend way to shutdown ThreadPool
        es.shutdown();
        try {
            // Wait at most 10 second
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            es.shutdownNow();
        }

        // Main thread executed after all sub threads done
        System.out.println("main thread done");
    }
}
