package com.cdfive.learn.thread.threadsafe;

import java.util.concurrent.CountDownLatch;

/**
 * @author cdfive
 */
public class NotThreadSafeTest1 {

    private static int i = 0;
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
                i++;
            }
            latch.countDown();
        }
    }
}
