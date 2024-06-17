package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.time.DateUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author cdfive
 */
public class LocalDateTest2 {

    public static void main(String[] args) {
        // ok
        LocalDate localDate = LocalDate.parse("2024-05-22", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("localDate=" + localDate.toString());

        // ok
        LocalDate localDate1 = LocalDate.parse("2024-05-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("localDate1=" + localDate1.toString());

        // error
        try {
            LocalDate localDate2 = LocalDate.parse("2024-05-22", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            System.out.println("localDate2 error," + e.getMessage());
        }

        // error
        try {
            LocalDate localDate3 = LocalDate.parse("2024-05-22 13:14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            System.out.println("localDate3 error," + e.getMessage());
        }

        LocalDate nowDate= LocalDate.now();
        LocalDate nowDatePlus3Days = nowDate.plusDays(3);
        // 3
        System.out.println(ChronoUnit.DAYS.between(nowDate, nowDatePlus3Days));
        // -3
        System.out.println(ChronoUnit.DAYS.between(nowDatePlus3Days, nowDate));

        // java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Seconds
        //	at java.time.LocalDate.until(LocalDate.java:1614)
//        System.out.println(Duration.between(nowDate, nowDatePlus3Days).toDays());
//        System.out.println(Duration.between(nowDatePlus3Days, nowDate).toDays());

        // 3
        System.out.println(ChronoUnit.DAYS.between(LocalDateTime.of(nowDate, LocalTime.MIN), LocalDateTime.of(nowDatePlus3Days, LocalTime.MIN)));
        // -3
        System.out.println(ChronoUnit.DAYS.between(LocalDateTime.of(nowDatePlus3Days, LocalTime.MIN), LocalDateTime.of(nowDate, LocalTime.MIN)));

        System.out.println("done");
    }
}
