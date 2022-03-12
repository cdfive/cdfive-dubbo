package com.cdfive.common.util;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cdfive
 */
public class ThreadUtilTest {

    private static final int threadNum = 10;
    private static final int totalTaskNum = 100;
    public static final AtomicInteger unProcessedThreadNum = new AtomicInteger(totalTaskNum);

    // May OOM, if too many tasks are submitted
    @Test
    public void testCase1() {
        doTestCase(defaultFixedThreadPool(threadNum));
    }

    // Avoid risk of OOM, but some tasks may not been executed,
    // since they are aborted, if tasks are submitted too fast
    @Test
    public void testCase2() {
        doTestCase(fixedThreadPoolWithAbortPolicy(threadNum));
    }

    // Avoid risk of OOM, but not high effective,
    // since some threads may wait for submit and have no task to execute at some time
    @Test
    public void testCase3() {
        doTestCase(fixedThreadPoolWithCallerRunsPolicy(threadNum));
    }

    // Avoid risk of OOM, high effective use of all threads
    @Test
    public void testCase4() {
        doTestCase(custormFixedThreadPool(threadNum));
    }

    public static void doTestCase(ThreadPoolExecutor threadPoolExecutor) {
        long start = System.currentTimeMillis();


        Thread statThread = new Thread(() -> printThreadPoolInfo(threadPoolExecutor));
        statThread.start();

        for (int i = 1; i <= totalTaskNum; i++) {
            try {
                threadPoolExecutor.submit(new Task(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(5, TimeUnit.MINUTES)) {
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            threadPoolExecutor.shutdownNow();
        }

        statThread.interrupt();

        // wait 3 second to exit
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ThreadUtilTest done,cost=" + (System.currentTimeMillis() - start) + "ms");
    }

    public static ThreadPoolExecutor defaultFixedThreadPool(int threadNum) {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);
    }

    public static ThreadPoolExecutor fixedThreadPoolWithAbortPolicy(int threadNum) {
        return new ThreadPoolExecutor(threadNum, threadNum,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPoolExecutor fixedThreadPoolWithCallerRunsPolicy(int threadNum) {
        return new ThreadPoolExecutor(threadNum, threadNum,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ThreadPoolExecutor custormFixedThreadPool(int threadNum) {
        return ThreadUtil.newFixedBlockingThreadPool(threadNum);
    }

    public static void printThreadPoolInfo(ThreadPoolExecutor executor) {
        boolean running = true;
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // Ignore exception
                running = false;
            }

            System.out.println("activeThreadNum=" + executor.getActiveCount() + ",queueSize=" + executor.getQueue().size()
                    + ",unProcessedThreadNum=" + ThreadUtilTest.unProcessedThreadNum.get());
        }
    }

    private static class Task implements Runnable {

        private int taskIndex;

        public Task(int taskIndex) {
            this.taskIndex = taskIndex;
        }

        @Override
        public void run() {
//            System.out.println(Thread.currentThread().getName() + "=>task[" + taskIndex + "] start");
            long start = System.currentTimeMillis();

            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ThreadUtilTest.unProcessedThreadNum.decrementAndGet();
//            System.out.println(Thread.currentThread().getName() + "=>task[" + taskIndex + "] end,cost=" + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
