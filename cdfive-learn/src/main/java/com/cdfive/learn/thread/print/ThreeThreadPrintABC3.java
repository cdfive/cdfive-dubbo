package com.cdfive.learn.thread.print;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Demo: Three threads print A,B,C sequentially.
 *
 * @author cdfive
 */
public class ThreeThreadPrintABC3 {

    public static void main(String[] args) {
        int max = 10;
        List<String> keys = Stream.of("A", "B", "C").collect(Collectors.toList());
        List<MyThread> threads = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {
            threads.add(new MyThread(keys.get(i), max, new Semaphore(i == 0 ? 1 : 0)));
        }

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).setReleaseSemaphore(threads.get((i + 1) % threads.size()).getAcquireSemaphore());
        }

        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
    }

    public static class MyThread extends Thread {

        private String key;

        private Integer max;

        private Semaphore acquireSemaphore;

        private Semaphore releaseSemaphore;

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
                System.out.println(Thread.currentThread().getName() + "=>" + key);
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
