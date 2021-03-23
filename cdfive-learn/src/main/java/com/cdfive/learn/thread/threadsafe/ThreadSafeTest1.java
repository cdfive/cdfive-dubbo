package com.cdfive.learn.thread.threadsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cdfive
 */
public class ThreadSafeTest1 {

    private static AtomicInteger i = new AtomicInteger();
    private static int nThread = 5;
    private static CountDownLatch latch = new CountDownLatch(nThread);

    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < nThread; j++) {
            new Task().start();
        }
        latch.await();
        System.out.println("main done");
        System.out.println("i=" + i);
    }

    public static class Task extends Thread {
        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i.incrementAndGet();
            }
            latch.countDown();
        }
    }
}
