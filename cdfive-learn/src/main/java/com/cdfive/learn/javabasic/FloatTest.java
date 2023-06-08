package com.cdfive.learn.javabasic;

import java.math.BigDecimal;

/**
 * @author cdfive
 */
public class FloatTest {

    public static void main(String[] args) {
        // java.lang.ArithmeticException: / by zero
        try {
            System.out.println(0 / 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // NaN
        System.out.println(0.0f / 0.0);

        // NaN
        System.out.println(0.0d / 0.0);

        // NaN
        System.out.println(0.0f / 0.0d);

        // NaN
        System.out.println(0.0d / 0.0f);

        // NaN
        System.out.println(0.0f / 0.0f);

        // NaN
        System.out.println(Float.NaN);

        // NaN
        System.out.println(Double.NaN);

        // 3.4028235E38
        System.out.println(Float.MAX_VALUE);

        // 1.4E-45
        System.out.println(Float.MIN_VALUE);

        // 340282346638528859811704183484516925440
        System.out.println(new BigDecimal(Float.MAX_VALUE));

        // 1.40129846432481707092372958328991613128026194187651577175706828388979108268586060148663818836212158203125E-45
        System.out.println(new BigDecimal(Float.MIN_VALUE));
    }
}
