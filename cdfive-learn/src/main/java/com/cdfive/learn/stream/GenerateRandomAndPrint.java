package com.cdfive.learn.stream;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author cdfive
 */
public class GenerateRandomAndPrint {

    public static void main(String[] args) {
        Stream.generate(() -> ThreadLocalRandom.current().nextInt(100)).limit(10).forEach(System.out::println);

        System.out.println(StringUtils.center("-", 20, "-"));

        IntStream.generate(() -> (int) (System.nanoTime() % 100)).limit(10).forEach(o -> System.out.println(o));
    }
}
