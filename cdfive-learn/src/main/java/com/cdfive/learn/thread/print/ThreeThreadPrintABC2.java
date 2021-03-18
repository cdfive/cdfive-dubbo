package com.cdfive.learn.thread.print;

import java.util.concurrent.Semaphore;

/**
 * Demo: Three threads print A,B,C sequentially.
 *
 * @author cdfive
 */
public class ThreeThreadPrintABC2 {

    public static void main(String[] args) {
        int max = 10;
        MyThread1 t1 = new MyThread1("A", max);
        MyThread2 t2 = new MyThread2("B", max);
        MyThread3 t3 = new MyThread3("C", max);
        t1.start();
        t2.start();
        t3.start();
    }

    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore(0);
    private static Semaphore s3 = new Semaphore(0);

    private static class MyThread1 extends Thread {
        private String key;

        private int max;

        public MyThread1(String key, int max) {
            this.key = key;
            this.max = max;
        }

        @Override
        public void run() {
            for (int i = 1; i <= max; i++) {
                s1.acquireUninterruptibly();
                System.out.println(Thread.currentThread().getName() + "=>" + key);
                s2.release();
            }
        }
    }

    private static class MyThread2 extends Thread {
        private String key;

        private int max;

        public MyThread2(String key, int max) {
            this.key = key;
            this.max = max;
        }

        @Override
        public void run() {
            for (int i = 0; i < max; i++) {
                s2.acquireUninterruptibly();
                System.out.println(Thread.currentThread().getName() + "=>" + key);
                s3.release();
            }
        }
    }

    private static class MyThread3 extends Thread {
        private String key;

        private int max;

        public MyThread3(String key, int max) {
            this.key = key;
            this.max = max;
        }

        @Override
        public void run() {
            for (int i = 0; i < max; i++) {
                s3.acquireUninterruptibly();
                System.out.println(Thread.currentThread().getName() + "=>" + key);
                s1.release();
            }
        }
    }
}
