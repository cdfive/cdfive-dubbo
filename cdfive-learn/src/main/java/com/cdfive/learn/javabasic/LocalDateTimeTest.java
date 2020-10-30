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
        System.out.println(localDateTime);
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(localDateTime));
    }
}
