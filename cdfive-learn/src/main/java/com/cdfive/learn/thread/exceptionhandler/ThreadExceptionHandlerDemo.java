package com.cdfive.learn.thread.exceptionhandler;

/**
 * @author cdfive
 */
public class ThreadExceptionHandlerDemo {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start");
                insert();
                System.out.println(Thread.currentThread().getName() + " end");
            }
        }, "biz thread");
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName() + " exception caught");
                e.printStackTrace();
            }
        });
        t.start();
    }

    public static void insert() {
        System.out.println("insert process start");
        if (true) {
            throw new RuntimeException("insert exception");
        }
        System.out.println("insert process end");
    }
}
