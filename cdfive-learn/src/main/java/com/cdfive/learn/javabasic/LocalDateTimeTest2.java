package com.cdfive.learn.javabasic;

import java.time.LocalDateTime;

/**
 * @author cdfive
 */
public class LocalDateTimeTest2 {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime beforeDawn = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        System.out.println(beforeDawn);

        LocalDateTime historyDay = beforeDawn.minusDays(90);
        System.out.println(historyDay);
    }
}
