package com.cdfive.learn.thread.q1;

/**
 * @author cdfive
 */
public class TwoThreadPrint1 {

//    int flag = 0;
    volatile int flag = 0;

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
            int i = 0;
            while (i <= 100) {
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
            int i = 1;
            while (i <= 99) {
                if (flag == 1) {
                    System.out.println(Thread.currentThread().getName() + "=>" + i);
                    i += 2;
                    flag = 0;
                }
            }
        }
    }
}
