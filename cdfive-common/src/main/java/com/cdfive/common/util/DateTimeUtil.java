package com.cdfive.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author cdfive
 */
public class DateTimeUtil {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        return DATE_TIME_FORMATTER.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static LocalDateTime parseLocalDateTime(String str) {
        return LocalDateTime.parse(str, DATE_TIME_FORMATTER);
    }

    public static Date parseDate(String str) {
        return Date.from(parseLocalDateTime(str).atZone(ZoneId.systemDefault()).toInstant());
    }
}
