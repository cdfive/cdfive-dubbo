package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author cdfive
 */
public class BigDecimalTest {

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("102.7");
        System.out.println(a);

        BigDecimal b = null;
        try {
            b = new BigDecimal("102.7 ");
            System.out.println(b);
        } catch (Exception e) {
            // java.lang.NumberFormatException
            e.printStackTrace();
        }

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        BigDecimal c = new BigDecimal("13.14");
        // 1314.00
        System.out.println(c.multiply(new BigDecimal("100")).toString());
        // 1314
        System.out.println(c.multiply(new BigDecimal("100")).toBigInteger());

        // 0.01
        System.out.println(new BigDecimal("1").divide(new BigDecimal("100")));
        // 0.1
        System.out.println(new BigDecimal("10").divide(new BigDecimal("100")));
        // 1
        System.out.println(new BigDecimal("100").divide(new BigDecimal("100")));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        try {
            BigDecimal nonTerminatingDecimal = new BigDecimal("1").divide(new BigDecimal("3"));
            System.out.println("nonTerminatingDecimal=" + nonTerminatingDecimal);
        } catch (Exception e) {
            // java.lang.NumberFormatException
            // Non-terminating decimal expansion; no exact representable decimal result.
            e.printStackTrace();
        }
    }
}
