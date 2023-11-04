package com.cdfive.learn.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class SemaphoreTest1 {

    public static void main(String[] args) {
        int max = 100;
        int concurrency = 5;
        Semaphore semaphore = new Semaphore(concurrency);
//        Semaphore semaphore = new Semaphore(concurrency, true);

        for (int i = 0; i < max; i++) {
            semaphore.acquireUninterruptibly();

            System.out.println(i + 1);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    semaphore.release();
                }
            }).start();
        }
    }
}
