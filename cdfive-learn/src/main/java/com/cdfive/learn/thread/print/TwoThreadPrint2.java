package com.cdfive.learn.thread.print;

/**
 * @author cdfive
 */
public class TwoThreadPrint2 {

    private static int num = 1;

    private static int max = 100;

    public static void main(String[] args) {
        Object obj = new Object();

        new Thread(() -> {
            while (true) {
                synchronized (obj) {
                    if (num > max) {
                        break;
                    }

                    System.out.println(Thread.currentThread().getName() + "=>" + (num++));

                    obj.notifyAll();

                    // !!! important,otherwise the thread is waiting
                    if (num >= max) {
                        break;
                    }

                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "T-1").start();

        new Thread(() -> {
            while (true) {
                synchronized (obj) {
                    if (num > max) {
                        break;
                    }

                    System.out.println(Thread.currentThread().getName() + "=>" + (num++));

                    obj.notifyAll();

                    // !!! important,otherwise the thread is waiting
                    if (num >= max) {
                        break;
                    }

                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "T-2").start();
    }
}
