package com.cdfive.learn.javabasic;

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
    }
}
