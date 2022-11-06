package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

/**
 * @author cdfive
 */
public class DoubleInfinityNaNTest {

    public static void main(String[] args) {
        double d = 1.0 / 0.0;
        System.out.println(d); // Infinity
        System.out.println(Double.isInfinite(d));// true
        System.out.println(Double.POSITIVE_INFINITY == d); // true

        double n = 0.0 / 0.0;
        System.out.println(n); // NaN
        System.out.println(Double.isNaN(n)); // true
        System.out.println(Double.NaN); // NaN

        float f = 1.0F / 0;
        System.out.println(f); // Infinity

        try {
            System.out.println(1/0);
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage()); // java.lang.ArithmeticException: / by zero
        }

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        System.out.println(Double.MIN_VALUE); // 4.9E-324
        System.out.println(Double.MAX_VALUE); // 1.7976931348623157E308

        System.out.println(Double.MIN_NORMAL); // 2.2250738585072014E-308
        System.out.println(Double.MAX_EXPONENT); // 1023
    }
}
