package com.cdfive.common.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author cdfive
 */
public class DateTimeUtilTest {

    @Test
    public void test() {
        String str = "2022-11-07 13:14:52";

        LocalDateTime localDateTime = DateTimeUtil.parseLocalDateTime(str);
        System.out.println(localDateTime);
        System.out.println(DateTimeUtil.formatLocalDateTime(localDateTime));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        Date date = DateTimeUtil.parseDate(str);
        System.out.println(date);
        System.out.println(DateTimeUtil.formatDate(date));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        LocalDate nowDate = LocalDate.now();
        System.out.println(nowDate);
        LocalDate prevWeekDate = DateTimeUtil.getPrevWeekDate();
        System.out.println(prevWeekDate);

        LocalDate weekStartDate = DateTimeUtil.getWeekStartDate();
        System.out.println(weekStartDate);
        LocalDate weekEndDate = DateTimeUtil.getWeekEndDate();
        System.out.println(weekEndDate);
    }
}
