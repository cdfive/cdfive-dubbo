package com.cdfive.common.util;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * @author cdfive
 */
public class ThreadPoolUtil {

    public static ThreadPoolExecutor newFixedBlockingThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static ThreadPoolExecutor newFixedBlockingThreadPool(int nThreads, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static ThreadPoolExecutor newFixedBlockingThreadPool(int nThreads, String threadNamePrefix) {
        return newFixedBlockingThreadPool(nThreads, new CustomizableThreadFactory(threadNamePrefix));
    }

    public static ThreadPoolExecutor newSingleThreadExecutorDiscard() {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS
                , new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardPolicy());
    }
}
