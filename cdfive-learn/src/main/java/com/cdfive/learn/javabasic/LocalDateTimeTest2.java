package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

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

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        Date date = new Date();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date);
        System.out.println(localDateTime);
        System.out.println(newDate);

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        LocalDate nowDate = LocalDate.now();
        System.out.println(nowDate);
        LocalDateTime nowDateMin = LocalDateTime.of(nowDate, LocalTime.MIN);
        System.out.println(nowDateMin);
        LocalDateTime nowDateMax = LocalDateTime.of(nowDate, LocalTime.MAX);
        System.out.println(nowDateMax);

        LocalDateTime min = LocalDateTime.of(LocalDate.from(LocalDateTime.now()), LocalDateTime.MIN.toLocalTime());
        System.out.println(min);
        LocalDateTime max = LocalDateTime.of(LocalDate.from(LocalDateTime.now()), LocalDateTime.MAX.toLocalTime());
        System.out.println(max);
    }
}
