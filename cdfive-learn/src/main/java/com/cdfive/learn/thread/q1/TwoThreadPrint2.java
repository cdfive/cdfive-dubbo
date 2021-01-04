package com.cdfive.learn.thread.q1;

/**
 * wait()
 * Exception in thread "T-1" java.lang.IllegalMonitorStateException
 * Exception in thread "T-2" java.lang.IllegalMonitorStateException
 *
 * => obj.wait()
 *
 * @author cdfive
 */
public class TwoThreadPrint2 {

    private static int flag = 0;

    private static int max = 100;

    public static void main(String[] args) {
        Object obj = new Object();

        new Thread(new Runnable() {
            int num = 0;

            @Override
            public void run() {
                boolean finished = false;
                while (!finished) {
                    synchronized (obj) {
                        if (flag != 0) {
                            try {
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        for (int i = 0; i < 5; i++) {
                            System.out.println(Thread.currentThread().getName() + "=>" + num);
                            num += 2;
                            if (num > max) {
                                finished = true;
                                break;
                            }
                        }

                        flag = 1;
                        obj.notifyAll();
                    }
                }
            }
        }, "T-1").start();

        new Thread(new Runnable() {
            int num = 1;

            @Override
            public void run() {
                boolean finished = false;
                while (!finished) {
                    synchronized (obj) {
                        if (flag != 1) {
                            try {
                                obj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        for (int i = 0; i < 5; i++) {
                            System.out.println(Thread.currentThread().getName() + "=>" + num);
                            num += 2;
                            if (num > max) {
                                finished = true;
                                break;
                            }
                        }

                        flag = 0;
                        obj.notifyAll();
                    }
                }
            }
        }, "T-2").start();
    }
}
