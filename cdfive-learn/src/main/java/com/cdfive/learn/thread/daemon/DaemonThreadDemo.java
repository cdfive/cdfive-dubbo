package com.cdfive.learn.thread.daemon;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class DaemonThreadDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    try {
                        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 3));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + "=>process,cost=" + (System.currentTimeMillis() - start) + "ms");
                }
            }, "t" + (i + 1));

            // Since sub threads is daemon thread, they exit as main thread exit
            t.setDaemon(true);
            t.start();
        }
        System.out.println("main done");
    }
}
