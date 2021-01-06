package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class StringEquals {

    public static void main(String[] args) {
        testEquals();
    }

    public static void testEquals() {
        String s1 = "hello";
        String s2 = "hello";
        String s3 = "he" + "llo";
        String s4 = "hel" + new String("lo");
        String s5 = new String("hello");
        String s6 = s5.intern();
        String s7 = "h";
        String s8 = "ello";
        String s9 = s7 + s8;
        // true
        System.out.println(s1 == s2);
        // true
        System.out.println(s1 == s3);
        // false
        System.out.println(s1 == s4);
        // false
        System.out.println(s1 == s9);
        // false
        System.out.println(s4 == s5);
        // true
        System.out.println(s1 == s6);
    }
}
