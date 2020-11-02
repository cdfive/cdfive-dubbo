package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class IntegerTest {
    public static void main(String[] args) {
        Integer a = 12300;
        System.out.println(a / 100.0);// 123.0
        a = 1230;
        System.out.println(a / 100.0);// 12.3
        a = 12310;
        System.out.println(a / 100.0);// 123.1
        a = 12315;
        System.out.println(a / 100.0);// 123.15
    }
}
