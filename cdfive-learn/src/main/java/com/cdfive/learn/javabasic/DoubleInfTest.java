package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class DoubleInfTest {
    public static void main(String[] args) {
        double d = 1.0 / 0.0;
        System.out.println(d); // Infinity

        System.out.println(Double.MIN_VALUE); // 4.9E-324
        System.out.println(Double.MAX_VALUE); // 1.7976931348623157E308

        System.out.println(Double.MIN_NORMAL); // 2.2250738585072014E-308
        System.out.println(Double.MAX_EXPONENT); // 1023
    }
}
