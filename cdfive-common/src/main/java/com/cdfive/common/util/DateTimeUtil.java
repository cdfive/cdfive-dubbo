package com.cdfive.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        if (localDateTime == null) {
            return null;
        }

        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        return formatLocalDateTime(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static LocalDateTime parseLocalDateTime(String str) {
        if (str == null) {
            return null;
        }

        return LocalDateTime.parse(str, DATE_TIME_FORMATTER);
    }

    public static Date parseDate(String str) {
        if (str == null) {
            return null;
        }

        LocalDateTime localDateTime = parseLocalDateTime(str);
        if (localDateTime == null) {
            return null;
        }

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate getPrevWeekDate() {
        return LocalDate.now().minusDays(DayOfWeek.values().length);
    }


    public static LocalDate getWeekStartDate() {
        return getWeekStartDate(LocalDate.now());
    }

    public static LocalDate getWeekStartDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (DayOfWeek.MONDAY.equals(dayOfWeek)) {
            return date;
        }

        return date.minusDays(dayOfWeek.getValue() - DayOfWeek.MONDAY.getValue());
    }

    public static LocalDate getWeekEndDate() {
        return getWeekEndDate(LocalDate.now());
    }

    public static LocalDate getWeekEndDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
            return date;
        }

        return date.plusDays(DayOfWeek.SUNDAY.getValue() - dayOfWeek.getValue());
    }

    public static boolean inSameWeek(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        return getWeekStartDate(date1).equals(getWeekStartDate(date2));
    }
}
