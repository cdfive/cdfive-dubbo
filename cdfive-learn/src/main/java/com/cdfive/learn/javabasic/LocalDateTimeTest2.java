package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
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
    }
}
