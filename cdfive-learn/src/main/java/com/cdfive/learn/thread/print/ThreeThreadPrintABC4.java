package com.cdfive.learn.thread.print;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Demo: Three threads print A,B,C sequentially.
 *
 * @author cdfive
 */
public class ThreeThreadPrintABC4 {

    public static void main(String[] args) {
        int max = 10;

        // Keys to print
        List<String> keys = IntStream.range('A', 'C' + 1).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.toList());

        // Init thread, including key, max and acquireSemaphore
        List<MyThread> threads = IntStream.range(0, keys.size())
                .mapToObj(i -> new MyThread(keys.get(i), max, new Semaphore(i == 0 ? 1 : 0)))
                .collect(Collectors.toList());

        // Init thread's name and releaseSemaphore
        IntStream.range(0, threads.size()).forEach(i -> {
            threads.get(i).setName("Thread-" + (i + 1));
            threads.get(i).setReleaseSemaphore(threads.get(i + 1 < threads.size() ? i + 1 : 0).getAcquireSemaphore());
        });

        // Start all threads
        threads.forEach(t -> t.start());
    }

    public static class MyThread extends Thread {

        private String key;

        private Integer max;

        private Semaphore acquireSemaphore;

        private Semaphore releaseSemaphore;

        public MyThread() {

        }

        public MyThread(String key, Integer max) {
            this.key = key;
            this.max = max;
        }

        public MyThread(String key, Integer max, Semaphore acquireSemaphore) {
            this.key = key;
            this.max = max;
            this.acquireSemaphore = acquireSemaphore;
        }

        public MyThread(String key, Integer max, Semaphore acquireSemaphore, Semaphore releaseSemaphore) {
            this.key = key;
            this.max = max;
            this.acquireSemaphore = acquireSemaphore;
            this.releaseSemaphore = releaseSemaphore;
        }

        @Override
        public void run() {
            for (int i = 0; i < max; i++) {
                acquireSemaphore.acquireUninterruptibly();
                System.out.println(Thread.currentThread().getName() + "=>" + key + ",times=" + (i + 1));
                releaseSemaphore.release();
            }
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Semaphore getAcquireSemaphore() {
            return acquireSemaphore;
        }

        public void setAcquireSemaphore(Semaphore acquireSemaphore) {
            this.acquireSemaphore = acquireSemaphore;
        }

        public Semaphore getReleaseSemaphore() {
            return releaseSemaphore;
        }

        public void setReleaseSemaphore(Semaphore releaseSemaphore) {
            this.releaseSemaphore = releaseSemaphore;
        }
    }
}
