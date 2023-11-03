package com.cdfive.learn.datastructure.queue;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class LinkBlockingQueueTest1 {

    public static void main(String[] args) throws InterruptedException {
        int max = 100;
        int concurrency = 5;

        LinkedBlockingQueue<Boolean> queue = new LinkedBlockingQueue<>(concurrency);
        queue.addAll(Arrays.asList(true, true, true, true, true));

        for (int i = 0; i < max; i++) {
            queue.take();

            int value = i + 1;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(value);
                        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // queue.put(value);
                    queue.add(true);
                }
            }).start();
        }
    }
}
