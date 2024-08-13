package com.cdfive.learn.thread.threadlocal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class ThreadLocalDemo {

    private static final ThreadLocal<UserContext> USER_CONTEXT = ThreadLocal.withInitial(() -> new UserContext());

    public static void main(String[] args) {
        // null
        System.out.println(getUserId());

        // main=>null
        printCurrentThreadUserId();

        setUserId(1);

        // main=>1
        printCurrentThreadUserId();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Thread-0=>null
                printCurrentThreadUserId();

                sleepSecond(2);

                setUserId(2);
                // Thread-0=>2
                printCurrentThreadUserId();

                // Thread-0=>done
                printCurrentThreadDone();
            }
        }).start();

        // main=>1
        printCurrentThreadUserId();

        sleepSecond(3);

        // main=>1
        printCurrentThreadUserId();

        // main=>done
        printCurrentThreadDone();
    }

    private static void printCurrentThreadUserId() {
        printCurrentThreadMsg(getUserId());
    }

    private static void printCurrentThreadDone() {
        printCurrentThreadMsg("done");
    }

    private static void printCurrentThreadMsg(Object msg) {
        System.out.println(Thread.currentThread().getName() + "=>" + msg);
    }

    private static void sleepSecond(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Integer getUserId() {
        return USER_CONTEXT.get().getUserId();
    }

    private static void setUserId(Integer userId) {
        USER_CONTEXT.set(new UserContext(userId));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class UserContext {
        private Integer userId;
    }
}
