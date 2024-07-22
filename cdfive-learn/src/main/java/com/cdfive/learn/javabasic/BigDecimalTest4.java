package com.cdfive.learn.javabasic;

import java.math.BigDecimal;

/**
 * @author cdfive
 */
public class BigDecimalTest4 {

    public static void main(String[] args) {
        // java.lang.NumberFormatException
        BigDecimal val = new BigDecimal("a");
        System.out.println(val);
    }
}
