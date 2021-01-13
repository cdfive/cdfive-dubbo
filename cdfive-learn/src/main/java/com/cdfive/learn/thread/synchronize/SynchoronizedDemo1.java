package com.cdfive.learn.thread.synchronize;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * synchronized非static方法，多线程不同对象访问
 * 并未同步执行
 * 因为MyThread的run方法中new了不同的MySync对象
 *
 * @author cdfive
 */
public class SynchoronizedDemo1 {

    public static void main(String[] args) {
        int threadNum = 3;
        for (int i = 0; i < threadNum; i++) {
            MyThread myThread = new MyThread();
            myThread.start();
        }
    }

    static class MySync {

        public synchronized void test() {
            long start = System.currentTimeMillis();
            System.out.println(String.format("%s start", Thread.currentThread().getName()));
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("%s end,cost=%dms", Thread.currentThread().getName(), (System.currentTimeMillis() - start)));
        }
    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            MySync sync = new MySync();
            sync.test();
        }
    }
}
