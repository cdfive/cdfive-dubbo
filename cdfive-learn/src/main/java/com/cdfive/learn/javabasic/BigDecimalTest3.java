package com.cdfive.learn.javabasic;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

        Long rechargeAmount = 699L;
        // 6.99
        System.out.println(new BigDecimal(String.valueOf(rechargeAmount)).divide(new BigDecimal("100")));
        // 7
        System.out.println(new BigDecimal(String.valueOf(rechargeAmount)).divide(new BigDecimal("100"), RoundingMode.HALF_DOWN).toString());
        // 7
        System.out.println(new BigDecimal(String.valueOf(rechargeAmount)).divide(new BigDecimal("100"), RoundingMode.HALF_UP).toString());
    }
}
