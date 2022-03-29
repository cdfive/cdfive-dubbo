package com.cdfive.learn.javabasic;

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
    }
}
