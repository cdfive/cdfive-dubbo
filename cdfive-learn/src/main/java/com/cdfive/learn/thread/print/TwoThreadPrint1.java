package com.cdfive.learn.thread.print;

/**
 * @author cdfive
 */
public class TwoThreadPrint1 {

    private volatile int flag = 0;

    private final int max = 100;

    public static void main(String[] args) {
        TwoThreadPrint1 obj = new TwoThreadPrint1();
        obj.go();
    }

    public void go() {
        new Thread(new Thread1(), "T-1").start();
        new Thread(new Thread2(), "T-2").start();
    }

    class Thread1 implements Runnable {
        @Override
        public void run() {
            int i = 1;
            while (i <= max) {
                if (flag == 0) {
                    System.out.println(Thread.currentThread().getName() + "=>" + i);
                    i += 2;
                    flag = 1;
                }
            }
        }
    }

    class Thread2 implements Runnable {
        @Override
        public void run() {
            int i = 2;
            while (i <= max) {
                if (flag == 1) {
                    System.out.println(Thread.currentThread().getName() + "=>" + i);
                    i += 2;
                    flag = 0;
                }
            }
        }
    }
}
