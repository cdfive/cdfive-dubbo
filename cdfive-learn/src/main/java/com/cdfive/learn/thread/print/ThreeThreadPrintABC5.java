package com.cdfive.learn.thread.print;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Demo: Three threads print A,B,C sequentially.
 *
 * @author cdfive
 */
public class ThreeThreadPrintABC5 {

    public static void main(String[] args) throws Exception {
        int max = 10;
        int num = 3;

        // Keys to print
        List<String> keys = IntStream.range('A', 'A' + num).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.toList());

        // Init thread, including key, max and acquireSemaphore
        List<PrintTask> tasks = IntStream.range(0, keys.size())
                .mapToObj(i -> new PrintTask(keys.get(i), max, new Semaphore(i == 0 ? 1 : 0)))
                .collect(Collectors.toList());

        // Init thread's name and releaseSemaphore
        IntStream.range(0, tasks.size()).forEach(i -> {
            tasks.get(i).setReleaseSemaphore(tasks.get(i + 1 < tasks.size() ? i + 1 : 0).getAcquireSemaphore());
        });

        // Using a fixed thread pool to execute all tasks
        ExecutorService es = Executors.newFixedThreadPool(tasks.size());
        es.invokeAll(tasks);
        es.shutdown();

        System.out.println("done");
    }

    public static class PrintTask implements Callable<Object> {

        private String key;

        private Integer max;

        private Semaphore acquireSemaphore;

        private Semaphore releaseSemaphore;

        public PrintTask() {

        }

        public PrintTask(String key, Integer max) {
            this.key = key;
            this.max = max;
        }

        public PrintTask(String key, Integer max, Semaphore acquireSemaphore) {
            this.key = key;
            this.max = max;
            this.acquireSemaphore = acquireSemaphore;
        }

        public PrintTask(String key, Integer max, Semaphore acquireSemaphore, Semaphore releaseSemaphore) {
            this.key = key;
            this.max = max;
            this.acquireSemaphore = acquireSemaphore;
            this.releaseSemaphore = releaseSemaphore;
        }

        @Override
        public Object call() throws Exception {
            for (int i = 0; i < max; i++) {
                acquireSemaphore.acquireUninterruptibly();
                System.out.println(Thread.currentThread().getName() + "=>" + key + ",times=" + (i + 1));
                releaseSemaphore.release();
            }

            return null;
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
