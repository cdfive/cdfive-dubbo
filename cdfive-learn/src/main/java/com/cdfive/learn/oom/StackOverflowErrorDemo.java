package com.cdfive.learn.oom;

/**
 * java.lang.StackOverflowError
 *
 * @author cdfive
 */
public class StackOverflowErrorDemo {

    public static void main(String[] args) {
        System.out.println(foo(1));
        System.out.println(foo(2));
        System.out.println(foo(3));
        System.out.println(foo(5));
        System.out.println(foo(10));
    }

    public static int foo(int n) {
//        if (n == 1 || n == 2) { // Right
        if (n == 1) { // Error, cause infinite recursion
            return 1;
        }


        return foo(n - 1) + foo(n - 2);
    }
}
