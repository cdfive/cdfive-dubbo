package com.cdfive.learn.javabasic;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author cdfive
 */
public class LocalDateTimeTest {
    public static void main(String[] args) {
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
    }
}
