package com.cdfive.learn.thread.waitnotify;

import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class ThreadWaitNotifyDemo {

    static Object obj = new Object();

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (obj) {
                    obj.wait();
                    System.out.println("Thread A do business!!!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (obj) {
                    obj.wait();
                    System.out.println("Thread B do business!!!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadC implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                obj.notify();
//                obj.notifyAll();
                System.out.println("Thread C do business!!!");
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Thread a = new Thread(new ThreadA());
        Thread b = new Thread(new ThreadB());
        Thread c = new Thread(new ThreadC());

        a.start();
        TimeUnit.MILLISECONDS.sleep(20);

        b.start();
        TimeUnit.MILLISECONDS.sleep(20);

        c.start();
        TimeUnit.MILLISECONDS.sleep(500);

        synchronized (obj) {
            obj.notify();
        }

        System.out.println("main done!!!");
    }
}
