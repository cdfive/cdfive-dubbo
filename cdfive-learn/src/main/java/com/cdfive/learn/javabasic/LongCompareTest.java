package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class LongCompareTest {

    public static void main(String[] args) {
        Long a = 343651486783377411L;
        Long b = 343651486783377411L;

        System.out.println(a == b); // false
        System.out.println(a.equals(b)); // true

        long aa = 343651486783377411L;
        long bb = 343651486783377411L;
        System.out.println(aa == bb); // true
    }
}
