package com.cdfive.learn.guava;

import com.google.common.base.Stopwatch;

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
    }
}
