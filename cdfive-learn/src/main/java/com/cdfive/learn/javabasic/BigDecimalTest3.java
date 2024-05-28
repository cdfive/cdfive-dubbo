package com.cdfive.learn.javabasic;

import java.math.BigDecimal;

/**
 * @author cdfive
 */
public class BigDecimalTest3 {

    public static void main(String[] args) {
        String value;
        BigDecimal bigDecimal;

        value = "0";
        bigDecimal = new BigDecimal(value.toString()).movePointLeft(2);
        // 0.00
        System.out.println(bigDecimal);
        // 0
        System.out.println(bigDecimal.stripTrailingZeros().toPlainString());

        value = "1230";
        bigDecimal = new BigDecimal(value.toString()).movePointLeft(2);
        // 12.30
        System.out.println(bigDecimal);
        // 12.3
        System.out.println(bigDecimal.stripTrailingZeros().toPlainString());
    }
}
