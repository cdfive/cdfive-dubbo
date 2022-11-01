package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author cdfive
 */
public class LocalDateTimeTest1 {

    public static void main(String[] args) {
        // now of LocalDate
        LocalDate nowDate = LocalDate.now();
        System.out.println(nowDate);
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(nowDate));
        try {
            System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(nowDate));
        } catch (Exception e) {
            // Unsupported field: HourOfDay
            System.out.println("format nowDate error," + e.getMessage());
        }

        // now of LocalDateTime
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println(nowDateTime);
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(nowDateTime));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(nowDateTime));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        // create LocalDate and LocalDateTime
        System.out.println(LocalDate.of(2022, 11, 1));
        System.out.println(LocalDateTime.of(2022, 11, 1, 13, 14, 52));
        System.out.println(LocalDateTime.of(LocalDate.of(2022, 11, 1), LocalTime.of(13, 14, 52)));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(1577808000000L), ZoneId.systemDefault());
        // 2020-01-01T00:00
        System.out.println(localDateTime);

        // 2020-01-01 00:00:00.000
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(localDateTime));

        // {},ISO resolved to 2021-06-29T11:11:11
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse("2021-06-29 11:11:11"));

        // Unable to obtain Instant from TemporalAccessor: {},ISO resolved to 2021-06-29T11:11:11 of type java.time.format.Parsed
//        System.out.println(Instant.from(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse("2021-06-29 11:11:11")));

        System.out.println(LocalDateTime.parse("2021-06-29 11:11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Unsupported field: InstantSeconds
//        System.out.println(Instant.from(LocalDateTime.parse("2021-06-29 11:11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        // Tue Jun 29 11:11:11 CST 2021
        System.out.println(Date.from(LocalDateTime.parse("2021-06-29 11:11:11", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant()));
    }
}
