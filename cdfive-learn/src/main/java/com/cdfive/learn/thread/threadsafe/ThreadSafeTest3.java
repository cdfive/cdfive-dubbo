package com.cdfive.learn.thread.threadsafe;

import java.util.concurrent.CountDownLatch;

/**
 * @author cdfive
 */
public class ThreadSafeTest3 {

    private static Item item = new Item(0, 0);
    private static int nThread = 2;
    private static CountDownLatch latch = new CountDownLatch(nThread);

    public static void main(String[] args) throws InterruptedException {
        for (int k = 0; k < nThread; k++) {
            new Task(k == 0).start();
        }
        latch.await();
        System.out.println("main done");
        System.out.println("item.i=" + item.i);
        System.out.println("item.j=" + item.j);
    }

    public static class Task extends Thread {

        private boolean flag;

        public Task(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++) {
                if (flag) {
                    item.i++;
                } else {
                    item.j++;
                }
            }
            latch.countDown();
        }
    }

    public static class Item {
        public Integer i;

        public Integer j;

        public Item(Integer i, Integer j) {
            this.i = i;
            this.j = j;
        }
    }
}
