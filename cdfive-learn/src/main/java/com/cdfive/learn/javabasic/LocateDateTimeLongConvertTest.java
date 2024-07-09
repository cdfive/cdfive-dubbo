package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author cdfive
 */
public class LocateDateTimeLongConvertTest {

    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);

        System.out.println(StringUtils.center("分隔符", 50, "-"));

        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        System.out.println(localDateTime1);

        // Note: use `ZoneOffset.of("+8")` instead of ZoneOffset.UTC
        // long long1 = localDateTime1.toInstant(ZoneOffset.UTC).toEpochMilli();
        long long1 = localDateTime1.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(long1);

        System.out.println(StringUtils.center("分隔符", 50, "-"));

        LocalDateTime localDateTime2 = new Date(timestamp).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(localDateTime2);

        long long2 = Timestamp.valueOf(localDateTime2).getTime();
        System.out.println(long2);
    }
}
