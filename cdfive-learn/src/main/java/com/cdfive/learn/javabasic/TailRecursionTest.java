package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

/**
 * @author cdfive
 */
public class TailRecursionTest {

    public static void main(String[] args) {
        // 120
        System.out.println(factorialRecursion(5));
        // 120
        System.out.println(factorialTailRecursion(1, 5));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        // java.lang.StackOverflowError
        try {
            System.out.println(factorialRecursion(100000));
        } catch (Throwable t) {
            System.out.println(t.getClass().getName());
        }
        // java.lang.StackOverflowError also
        try {
            System.out.println(factorialTailRecursion(1, 100000));
        } catch (Throwable t) {
            System.out.println(t.getClass().getName());
        }
    }

    public static int factorialRecursion(int number) {
        if (number == 1) {
            return 1;
        }

        return number * factorialRecursion(number - 1);
    }

    public static int factorialTailRecursion(final int factorial, final int number) {
        if (number == 1) {
            return factorial;
        } else {
            return factorialTailRecursion(factorial * number, number - 1);
        }
    }
}
