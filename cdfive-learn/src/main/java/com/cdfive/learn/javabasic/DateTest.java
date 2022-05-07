package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author cdfive
 */
public class DateTest {

    public static void main(String[] args) {
        // java min date
        Date date = new Date(0);

        // Thu Jan 01 08:00:00 CST 1970
        System.out.println(date);

        // 1970-01-01 08:00:00.000
        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
