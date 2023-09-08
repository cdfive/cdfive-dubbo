package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
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

        // 2023-09-05
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        // TUESDAY
        System.out.println(dayOfWeek);
        // 2
        System.out.println(dayOfWeek.getValue());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 3
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
    }
}
