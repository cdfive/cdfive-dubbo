package com.cdfive.learn.thread.print;

import java.util.concurrent.Semaphore;

/**
 * @author cdfive
 */
public class TwoThreadPrint4 {

    private static int num = 1;

    private static int max = 100;

    public static void main(String[] args) {
        Semaphore semaphore1 = new Semaphore(1, true);
        Semaphore semaphore2 = new Semaphore(0, true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (num >= max) {
                        break;
                    }

                    semaphore1.acquireUninterruptibly();
                    System.out.println(Thread.currentThread().getName() + "=>" + (num++));
                    semaphore2.release();
                }
            }
        }, "T-1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (num >= max) {
                        break;
                    }

                    semaphore2.acquireUninterruptibly();
                    System.out.println(Thread.currentThread().getName() + "=>" + (num++));
                    semaphore1.release();
                }
            }
        }, "T-2").start();
    }
}
