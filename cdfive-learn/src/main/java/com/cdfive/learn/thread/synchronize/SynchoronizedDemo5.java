package com.cdfive.learn.thread.synchronize;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 在非static方法里的synchronized Xxx.class，多线程不同对象访问
 * 同步执行成功
 * synchronized Xxx.class相当于全局锁
 *
 * @author cdfive
 */
public class SynchoronizedDemo5 {

    public static void main(String[] args) {
        int threadNum = 3;
        for (int i = 0; i < threadNum; i++) {
            MyThread myThread = new MyThread();
            myThread.start();
        }
    }

    static class MySync {

        public void test() {
            synchronized (SynchoronizedDemo5.class) {
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
    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            MySync sync = new MySync();
            sync.test();
        }
    }
}
