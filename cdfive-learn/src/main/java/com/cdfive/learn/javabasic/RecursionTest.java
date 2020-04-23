package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class RecursionTest {

    public static void main(String[] args) {
        System.out.println(foo(3));
    }

    public static int foo(int a) {
        if (a <= 0) {
            return 0;
        }

        a--;

        int b = foo(a);
        return b;
    }
}
