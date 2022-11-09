package com.cdfive.learn.thread.print;

import java.util.concurrent.locks.Condition;
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
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            while (true) {
                if (num >= max) {
                    break;
                }

                lock.lock();
                try {
                    if (num % 2 == 0) {
                        condition1.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "=>" + (num++));
                    condition2.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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

                lock.lock();
                try {
                    if (num % 2 == 1) {
                        condition2.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "=>" + (num++));
                    condition1.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }

        }, "T-2").start();
    }
}
