package com.cdfive.learn.thread.interrupt;

/**
 * @author cdfive
 */
public class ThreadInterruptDemo2 {

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    if (Thread.currentThread().isInterrupted()) {
                        // if thread is interrupted, break loop and the thread exit
                        break;
                    }
                }
            }
        });

        thread.start();
        thread.interrupt();
        System.out.println(thread.isInterrupted()); // true
        System.out.println(thread.interrupted()); // false currentThread is main
        System.out.println(Thread.interrupted()); // false currentThread is main
        System.out.println(thread.isInterrupted()); // false, the interrupt state has been reset

        thread.join();
        System.out.println("main thread done"); // will be executed
    }
}
