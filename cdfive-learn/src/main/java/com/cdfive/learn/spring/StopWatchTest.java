package com.cdfive.learn.spring;

import org.springframework.util.StopWatch;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class StopWatchTest {

    public static void main(String[] args) throws InterruptedException {
        StopWatch sw = new StopWatch("统计开始");
        sw.start("aaa");
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2) + 1);
        sw.stop();
        sw.start("bbb");
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2) + 1);
        sw.stop();
        sw.start("ccc");
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2) + 1);
        sw.stop();
        System.out.println(sw.prettyPrint());
    }
}
