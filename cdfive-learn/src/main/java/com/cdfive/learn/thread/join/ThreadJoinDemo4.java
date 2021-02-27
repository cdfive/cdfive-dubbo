package com.cdfive.learn.thread.join;

import org.omg.CORBA.TIMEOUT;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class ThreadJoinDemo4 {

    public static void main(String[] args) {
        Thread prevThread = null;
        JoinThread t = null;
        for (int i = 0; i < 10; i++) {
            t = new JoinThread(prevThread, i);
            prevThread = t;
            t.start();
        }

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread done");
    }

    public static class JoinThread extends Thread {

        private Thread prevThread;

        private int num;

        public JoinThread(Thread prevThread, int num) {
            this.prevThread = prevThread;
            this.num = num;
        }

        @Override
        public void run() {
            if (prevThread != null) {
                try {
                    prevThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10, 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "=>" + num);
        }
    }
}
