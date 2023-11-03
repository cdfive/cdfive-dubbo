package com.cdfive.learn.datastructure.queue;

import lombok.Data;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class LinkBlockingQueueTest4 {

    public static void main(String[] args) {
        int max = 100;
        int concurrency = 5;

        ConcurrentLimitor limitor = new ConcurrentLimitor(5);
        for (int i = 0; i < max; i++) {
            int value = i + 1;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    limitor.await();

                    System.out.println(value);
                    try {
                        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    limitor.done();
                }
            }).start();
        }
    }

    @Data
    private static class ConcurrentLimitor {

        private int concurrency;

        private LinkedBlockingQueue<Boolean> queue;

        public ConcurrentLimitor(int concurrency) {
            this.concurrency = concurrency;
            this.queue = new LinkedBlockingQueue<>(concurrency);
        }

        public void await() {
            try {
                this.queue.put(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void done() {
            this.queue.poll();
        }
    }
}
