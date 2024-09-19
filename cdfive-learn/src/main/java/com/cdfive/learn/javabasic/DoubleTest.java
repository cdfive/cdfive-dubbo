package com.cdfive.learn.javabasic;

import java.math.BigDecimal;

/**
 * @author cdfive
 */
public class DoubleTest {

    public static void main(String[] args) {
        // 7950.0
        System.out.println(79.5 * 100);
        // 7940.000000000001
        System.out.println(79.4 * 100);
        // 7959.999999999999
        System.out.println(79.6 * 100);

        // 0.30000000000000004
        System.out.println(0.1 * 3);

        // 0.30000000000000004
        System.out.println(0.1 + 0.2);
        // 0.30000000000000004
        System.out.println(0.1D + 0.2D);
        // 1.0
        System.out.println(0.1 + 0.2 + 0.7);
        // 0.9
        System.out.println(0.1 + 0.2 + 0.6);

        double d = 0.1;
        System.out.println(d * 3); // 0.30000000000000004
        System.out.println(d * 0.3); // 0.03

        System.out.println((int) d); // 0
        System.out.println((int) (d * 3)); // 0
        System.out.println((int) (d * 9)); // 0
        System.out.println((int) (d * 11)); // 1
        System.out.println((int) (d * 0.5)); // 0
        System.out.println((int) d * 0.5); // 0.0

        System.out.println(0.1 * 3 == 0.3); // false
        System.out.println(0.3 * 1 == 0.3); // true
        System.out.println(0.1F * 3 == 0.3); // false
        System.out.println(0.1F * 3 == 0.3F); // true
        System.out.println(0.1D * 3 == 0.3D); // false
        System.out.println(0.1 * 3 == 0.3D); // false

        System.out.println(19.9); // 19.9
        System.out.println(19.9D); // 19.9
        Double tmpD1 = 19.9;
        System.out.println(tmpD1); // 19.9
        Double tmpD2 = 19.9D;
        System.out.println(tmpD2); // 19.9
        System.out.println(19.9 * 100); // 1989.9999999999998
        System.out.println(20.9 * 100); // 2090.0
        System.out.println(9.9 * 100); // 999.0
        System.out.println(new BigDecimal("19.9").multiply(new BigDecimal("100"))); // 1990.0
        System.out.println(new BigDecimal("19.9").multiply(new BigDecimal("100")).setScale(0)); // 1990
        System.out.println(new BigDecimal("19.9").multiply(new BigDecimal("100")).stripTrailingZeros()); // 1.99E+3
        System.out.println(new BigDecimal("19.9").multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString()); // 1990
        System.out.println(new BigDecimal("19.9").multiply(new BigDecimal("100")).toBigInteger()); // 1990
        System.out.println(new BigDecimal("19.9").multiply(new BigDecimal("100")).toBigInteger().intValue()); // 1990
        System.out.println(new BigDecimal("19.9").multiply(new BigDecimal("100")).intValue()); // 1990

        Double str1 = 12.0D;
        System.out.println(str1); // 12.0
        Double str2 = 12.00D;
        System.out.println(str2); // 12.0
        Double str3 = 12D;
        System.out.println(str3); // 12.0
        Double str4 = Double.parseDouble("12.00");
        System.out.println(str4); // 12.0
    }
}
