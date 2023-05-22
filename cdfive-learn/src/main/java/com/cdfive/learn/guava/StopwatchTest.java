package com.cdfive.learn.guava;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class StopwatchTest {

    public static void main(String[] args) throws Exception {
        Stopwatch sw = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(sw.elapsed(TimeUnit.MILLISECONDS));
        TimeUnit.SECONDS.sleep(2);
        System.out.println(sw.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(StringUtils.center("分隔线", 50, "-"));

        Stopwatch sw2 = Stopwatch.createStarted();
        try {
            sw2.start();
        } catch (Exception e) {
            // This stopwatch is already running.
            System.out.println(e.getMessage());
        }
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
        sw2.stop();
        sw2.start();
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
        sw2.stop();
        sw2.start();
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
        sw2.stop();
        System.out.println(sw2.toString());
        System.out.println(sw2.elapsed(TimeUnit.MICROSECONDS));
        System.out.println(sw2.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(sw2.elapsed(TimeUnit.SECONDS));
    }
}
