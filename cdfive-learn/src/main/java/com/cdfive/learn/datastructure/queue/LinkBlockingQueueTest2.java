package com.cdfive.learn.datastructure.queue;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class LinkBlockingQueueTest2 {

    public static void main(String[] args) throws InterruptedException {
        int max = 100;
        int concurrency = 5;

        LinkedBlockingDeque<Boolean> queue = new LinkedBlockingDeque<>(concurrency);

        for (int i = 0; i < max; i++) {
            queue.put(true);

            System.out.println((i + 1));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
//                        queue.take();
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        System.out.println("done");
    }
}
