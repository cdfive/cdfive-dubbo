package com.cdfive.learn.thread.lock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cdfive
 */
public class ConditionDemo2 {

    public static void main(String[] args) throws Exception {
        Item item = new Item();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                item.doWait();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                item.doNotify();
            }
        });

        t1.start();

        TimeUnit.SECONDS.sleep(4);

        t2.start();
    }

    private static class Item {

        private final ReentrantLock lock = new ReentrantLock();

        private final Condition condition = lock.newCondition();

        public void doWait() {
            try {
                lock.lock();
                print("doWait start");
                long start = System.currentTimeMillis();
                boolean awaitResult = condition.awaitUntil(Date.from(LocalDateTime.now().plusSeconds(3).atZone(ZoneId.systemDefault()).toInstant()));
                print("doWait end,awaitResult=" + awaitResult + ",cost=" + (System.currentTimeMillis() - start) + "ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void doNotify() {
            try {
                lock.lock();
                print("doNotify start");
                condition.signalAll();
                print("doNotify end");
            } finally {
                lock.unlock();
            }
        }
    }

    public static String formatNow() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
    }

    public static void print(String info) {
        System.out.println("[" + formatNow() + "]" + info);
    }
}
