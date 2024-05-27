package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author cdfive
 */
public class LocalDateTest2 {

    public static void main(String[] args) {
        // ok
        LocalDate localDate = LocalDate.parse("2024-05-22", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("localDate=" + localDate.toString());

        // ok
        LocalDate localDate1 = LocalDate.parse("2024-05-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("localDate1=" + localDate1.toString());

        // error
        try {
            LocalDate localDate2 = LocalDate.parse("2024-05-22", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            System.out.println("localDate2 error," + e.getMessage());
        }

        // error
        try {
            LocalDate localDate3 = LocalDate.parse("2024-05-22 13:14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            System.out.println("localDate3 error," + e.getMessage());
        }

        System.out.println("done");
    }
}
