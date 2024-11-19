package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class IntegerTest6 {

    public static void main(String[] args) {
        Integer a = 100000;
        Integer b = 100_000;
        Integer c = 10_0000;
        System.out.println(a.equals(b)); // true
        System.out.println(b.equals(c)); // true
        System.out.println(c.equals(a)); // true
    }
}
