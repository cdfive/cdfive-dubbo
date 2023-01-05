package com.cdfive.learn.thread.future;

import java.util.concurrent.*;

/**
 * @author cdfive
 */
public class FutureTest2 {

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
            System.out.println(result); // 666
        } catch (Exception e) {
            e.printStackTrace();
        }

        es.shutdown();
        System.out.println("FutureExceptionTest done");
    }

    private static String func() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            if (false) {
                throw new RuntimeException("test");
            }
            TimeUnit.MILLISECONDS.sleep(50);
            return "666";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
