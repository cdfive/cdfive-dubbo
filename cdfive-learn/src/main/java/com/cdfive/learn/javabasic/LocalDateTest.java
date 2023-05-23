package com.cdfive.learn.javabasic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author cdfive
 */
public class LocalDateTest {

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println("1=>" + now);

        LocalDate date = LocalDate.of(2023, 5, 20);
        System.out.println("2=>" + date);

        LocalDateTime dateTimeStart = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
        LocalDateTime dateTimeEnd = LocalDateTime.of(date, LocalTime.of(23, 59, 59));
        System.out.println("3=>" + dateTimeStart);
        System.out.println("4=>" + dateTimeEnd);
        System.out.println("5=>" + dateTimeStart.toLocalDate());
        System.out.println("6=>" + dateTimeEnd.toLocalDate());
    }
}
