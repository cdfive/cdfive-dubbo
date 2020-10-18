package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class TimestampTest {

    public static void main(String[] args) throws Exception {
        // usually yyyy-MM-dd HH:mm:ss.SSS, but maybe yyyy-MM-dd HH:mm:ss.SS or yyyy-MM-dd HH:mm:ss.S
        System.out.println(new Timestamp(System.currentTimeMillis()));
        System.out.println("------------------------");

        // test print many times
        for (int i = 0; i < 20; i++) {
            sleepAndPrintTime();
        }
        System.out.println("------------------------");

        // test custorm format
        String testTime = "2020-10-17 17:57:21.73";
        Date date = DateUtils.parseDate(testTime, "yyyy-MM-dd HH:mm:ss.SS");
        String dateStr = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(dateStr); // 2020-10-17 17:57:21.073
        System.out.println(date.getTime()); // 1602928641073
    }

    private static void sleepAndPrintTime() throws Exception {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}
