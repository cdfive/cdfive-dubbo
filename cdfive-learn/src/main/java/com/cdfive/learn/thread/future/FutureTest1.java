package com.cdfive.learn.thread.future;

import java.util.concurrent.*;

/**
 * @author cdfive
 */
public class FutureTest1 {

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newSingleThreadExecutor();

        Future<String> future = es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return func();
            }
        });

        try {
            Object result = future.get(200, TimeUnit.MILLISECONDS);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace(); // java.util.concurrent.ExecutionException: java.lang.RuntimeException: test
        }

        es.shutdown();
        System.out.println("FutureExceptionTest done");
    }

    private static String func() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            if (true) { // throw exception
                throw new RuntimeException("test");
            }
            TimeUnit.MILLISECONDS.sleep(50);
            return "666";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
