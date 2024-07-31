package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author cdfive
 */
public class BigDecimalTest4 {

    public static void main(String[] args) {
        case1();

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        case2();

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        case3();
    }

    private static void case1() {
        // java.lang.NumberFormatException
        try {
            BigDecimal val = new BigDecimal("a");
            System.out.println(val);
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
    private static void case2() {
        // java.lang.NullPointerException
        try {
            Double d = null;
            BigDecimal val = new BigDecimal(d);// unboxing
            System.out.println(val);
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }

    private static void case3() {
        // java.lang.NumberFormatException
        try {
            double d = 0 / 0d; // NaN
            BigDecimal val = new BigDecimal(d);
            System.out.println(val);
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
}
