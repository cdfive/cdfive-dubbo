package com.cdfive.learn.thread.print;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cdfive
 */
public class TwoThreadPrint3 {

    private static int num = 1;

    private static int max = 100;

    private static volatile boolean flag = true;

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread(() -> {
            while (true) {
                if (num >= max) {
                    break;
                }

                try {
                    lock.lock();
                    if (flag) {
                        System.out.println(Thread.currentThread().getName() + "=>" + (num++));
                        flag = false;
                    }
                } finally {
                    lock.unlock();
                }
            }

        }, "T-1").start();

        new Thread(() -> {
            while (true) {
                if (num >= max) {
                    break;
                }

                try {
                    lock.lock();
                    if (!flag) {
                        System.out.println(Thread.currentThread().getName() + "=>" + (num++));
                        flag = true;
                    }
                } finally {
                    lock.unlock();
                }
            }

        }, "T-2").start();
    }
}
