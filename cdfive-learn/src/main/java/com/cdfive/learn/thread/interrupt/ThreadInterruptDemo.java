package com.cdfive.learn.thread.interrupt;

/**
 * @author cdfive
 */
public class ThreadInterruptDemo {

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {

                }
            }
        });

        thread.start();
        thread.interrupt();
        System.out.println(thread.isInterrupted()); // true
        System.out.println(thread.interrupted()); // false currentThread is main
        System.out.println(Thread.interrupted()); // false currentThread is main
        System.out.println(thread.isInterrupted()); // true

        thread.join();
        System.out.println("main done"); // will not execute since the thread(in running state) is not really interrupted
    }
}
