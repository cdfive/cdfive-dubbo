package com.cdfive.learn.thread.print;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demo: Three threads print A,B,C sequentially.
 *
 * @author cdfive
 */
public class ThreeThreadPrintABC1 {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        MyThread threadA = new MyThread(lock, "A");
        MyThread threadB = new MyThread(lock, "B");
        MyThread threadC = new MyThread(lock, "C");
        threadA.start();
        threadB.start();
        threadC.start();
    }

    public static class MyThread extends Thread {

        private ReentrantLock lock;

        private String key;

        public MyThread(ReentrantLock lock, String key) {
            this.lock = lock;
            this.key = key;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "=>" + key);

                    // Since thread run too fast, here we need to sleep a small while when using fair ReentrantLock
                    // Otherwise, A->B->C may not been printed sequentially
                    try {
                        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
