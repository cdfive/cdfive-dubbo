package com.cdfive.learn.thread.join;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class ThreadJoinDemo {

    private static Thread t1 = null, t2 = null, t3 = null;

    public static void main(String[] args) {
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "start");
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100, 900));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "end");
            }
        }, "T1");

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "start");
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100, 900));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "end");
            }
        }, "T2");

        t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "start");
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100, 900));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "end");
            }
        }, "T3");

        t1.start();
        t2.start();
        t3.start();
    }
}
