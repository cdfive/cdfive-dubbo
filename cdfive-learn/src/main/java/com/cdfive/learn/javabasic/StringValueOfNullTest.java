package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class StringValueOfNullTest {
    public static void main(String[] args) {
        Long a = null;
        System.out.println("1." + String.valueOf(a)); // null
        System.out.println("2." + String.valueOf(null));
//        Exception in thread "main" java.lang.NullPointerException
//        at java.lang.String.<init>(String.java:166)
//        at java.lang.String.valueOf(String.java:3008)
    }
}
