package com.cdfive.learn.javabasic;

import java.util.Objects;

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

        Long x = Long.valueOf("-1");
        System.out.println(Objects.equals(x, -1)); // false
        System.out.println(Objects.equals(x, -1L)); // true
        System.out.println(Objects.equals(x, Long.valueOf(-1))); // true
        System.out.println(Objects.equals(x, Long.valueOf(-1L))); // true
        System.out.println(Objects.equals(x, Long.valueOf("-1"))); // true
    }
}
