package com.cdfive.learn.thread.join;

/**
 * @author cdfive
 */
public class ThreadJoinDemo3 {

    public static void main(String[] args) {
        Thread prevThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            JoinThread t = new JoinThread(prevThread, i);
            prevThread = t;
            t.start();
        }

        try {
            Thread.sleep(3_000);
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
            try {
                prevThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "=>" + num);
        }
    }
}
