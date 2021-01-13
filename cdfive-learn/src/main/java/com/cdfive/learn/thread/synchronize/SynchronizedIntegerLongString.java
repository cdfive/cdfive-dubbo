package com.cdfive.learn.thread.synchronize;

import com.cdfive.common.tool.Holder;
import lombok.Synchronized;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class SynchronizedIntegerLongString {

    public static void main(String[] args) {
        int threadNum = 100;
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    noSync();

//                    syncInteger(5); // success
//                    syncInteger(127); // success
//                    syncInteger(128);
//                    syncInteger(2020);
//                    syncInteger(-1); // success
//                    syncInteger(-128); // success
//                    syncInteger(-129);

//                    syncLong(1L); // success
//                    syncLong(127L); // success
//                    syncLong(128L);
//                    syncLong(-128L); // success
//                    syncLong(-129L);

//                    syncString("hello"); // success
//                    syncString("2020"); // success

//                    syncIntegerToString(5);
//                    syncIntegerToString(2020);

//                    syncIntegerToString2(5);

                    syncIntegerToString3(5); // success

//                    syncIntegerHolder(5);

//                    syncIntegerHolder2(5); // success
                }
            }).start();
        }

//        try {
//            System.out.println("main thread sleep 60 second");
//            TimeUnit.SECONDS.sleep(60);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("done");
    }

    public static void noSync() {
        doBiz("noSync");
    }

    public static void syncInteger(Integer i) {
//        System.out.println(Thread.currentThread().getName() + "=>" + System.identityHashCode(i));
        synchronized (i) {
            doBiz("syncInteger");
        }
    }

    public static void syncLong(Long l) {
        synchronized (l) {
            doBiz("syncLong");
        }
    }

    public static void syncString(String s) {
//        System.out.println(Thread.currentThread().getName() + "=>" + System.identityHashCode(s));
        synchronized (s) {
            doBiz("syncString");
        }
    }

    public static void syncIntegerToString(Integer i) {
        synchronized (String.valueOf(i)) {
//        synchronized (i + "") {
            doBiz("syncIntegerToString");
        }
    }

    public static void syncIntegerToString2(Integer i) {
        syncIntegerToString2Inner(String.valueOf(i));
    }

    public static void syncIntegerToString2Inner(String s) {
        synchronized (s) {
            doBiz("syncIntegerToString2Inner");
        }
    }

    public static void syncIntegerToString3(Integer i) {
        // Pay attention to the Usage of String#intern, important!!!
        synchronized (String.valueOf(i).intern()) {
            doBiz("syncIntegerToString3");
        }
    }

    public static void syncIntegerHolder(Integer i) {
        synchronized (new Holder(i)) {
            doBiz("syncIntegerHolder");
        }
    }

    public static void syncIntegerHolder2(Integer i) {
        synchronized (HolderHelper.getHolder(i)) {
            doBiz("syncIntegerHolder2");
        }
    }

    public static class HolderHelper {
        private static volatile Holder<Integer> holder;

        public static Holder<Integer> getHolder(Integer i) {
            if (holder == null) {
                synchronized (HolderHelper.class) {
                    if (holder == null) {
                        holder = new Holder<>(i);
                    }
                }
            }

            return holder;
        }
    }

    private static void doBiz(String desc) {
        System.out.println(String.format(Thread.currentThread().getName() + "=>%s start", desc));
        long start = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format(Thread.currentThread().getName() + "=>%s end,cost=%sms", desc, (System.currentTimeMillis() - start)));
    }
}
