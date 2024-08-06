package com.cdfive.learn.commonslang;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.Computable;
import org.apache.commons.lang3.concurrent.Memoizer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class MemoizerTest {

    public static void main(String[] args) throws InterruptedException {
        Memoizer<Integer, String> memoizer = new Memoizer<>(new Computable<Integer, String>() {
            @Override
            public String compute(Integer max) throws InterruptedException {
                int time = ThreadLocalRandom.current().nextInt(max);
                TimeUnit.SECONDS.sleep(time);
                return String.format("%d second passed, cdfive", time);
            }
        });
        System.out.println(memoizer.compute(5));
        System.out.println(memoizer.compute(5));
        System.out.println(memoizer.compute(5));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(memoizer.compute(5));
        }
        System.out.println(MemoizerTest.class.getSimpleName() + " done");
    }
}
