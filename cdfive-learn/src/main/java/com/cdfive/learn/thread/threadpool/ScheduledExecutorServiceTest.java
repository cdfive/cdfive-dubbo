package com.cdfive.learn.thread.threadpool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class ScheduledExecutorServiceTest {

    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);

        print("hello ");

        // wait 5 second and print cdfive
        ses.schedule(() -> print("cdfive"), 5, TimeUnit.SECONDS);

        // print done immediately
        print("done");

        ses.shutdown();
    }

    public static void print(String info) {
        System.out.println("[" + formatNow() + "]" + info);
    }

    public static String formatNow() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
    }
}
