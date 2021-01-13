package com.cdfive.learn.thread.synchronize;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * synchronized非static方法，多线程相同对象访问
 * 同步执行成功
 *
 * @author cdfive
 */
public class SynchoronizedDemo3 {

    public static void main(String[] args) {
        int threadNum = 3;
        MySync mySync = new MySync();
        for (int i = 0; i < threadNum; i++) {
            MyThread myThread = new MyThread(mySync);
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

        private MySync mySync;

        public MyThread(MySync mySync) {
            this.mySync = mySync;
        }

        @Override
        public void run() {
            mySync.test();
        }
    }
}
