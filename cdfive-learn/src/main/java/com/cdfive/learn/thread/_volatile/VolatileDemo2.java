package com.cdfive.learn.thread._volatile;

import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class VolatileDemo2 {

    public static void main(String[] args) {
        Task task = new Task();
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                task.setFlag(true);
                System.out.println("t2 done");
            }
        });
        t1.start();
        t2.start();
    }

    private static class Task implements Runnable {

        // volatile variable
        private volatile boolean flag = false;

        @Override
        public void run() {
            while (!flag) {

            }

            // Will be executed
            System.out.println("Task done");
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
