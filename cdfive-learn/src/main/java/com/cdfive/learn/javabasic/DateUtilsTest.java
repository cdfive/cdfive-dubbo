package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

/**
 * @author cdfive
 */
public class DateUtilsTest {

    public static void main(String[] args) throws Exception {
        System.out.println(StringUtils.center("case1", 50, "-"));
        String s1 = "090000";
        Date date1 = DateUtils.parseDate(s1, new String[]{"HH:mm:ss", "HHmmss"});
        // s1 can be parsed correctly
        System.out.println(date1);
        Time time1 = new Time(date1.getTime());
        System.out.println(time1);

        System.out.println(StringUtils.center("case1", 50, "-"));
        String s2 = "2022-03-32";
        Date date2 = DateUtils.parseDate(s2, new String[]{"yyyy-MM-dd"});
        // Note that we get date 2022-04-01
        System.out.println(date2);
    }
}
