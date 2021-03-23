package com.cdfive.learn.thread.threadsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cdfive
 */
public class ThreadSafeTest2 {

    private static Item item = new Item(new AtomicInteger());
    private static int nThread = 5;
    private static CountDownLatch latch = new CountDownLatch(nThread);

    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < nThread; j++) {
            new Task().start();
        }
        latch.await();
        System.out.println("main done");
        System.out.println("item.i=" + item.i);
    }

    public static class Task extends Thread {
        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                item.i.incrementAndGet();
            }
            latch.countDown();
        }
    }

    public static class Item {
        public AtomicInteger i;

        public Item(AtomicInteger i) {
            this.i = i;
        }
    }
}
