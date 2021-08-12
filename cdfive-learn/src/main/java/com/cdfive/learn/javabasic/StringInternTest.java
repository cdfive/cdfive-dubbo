package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class StringInternTest {

    public static void main(String[] args) {
        test_intern_1();
        test_intern_2();
        test_intern_3();
    }

    public static void test_intern_1() {
        String s1 = new String("123") + new String("123");
        s1.intern();
        String s2 = "123123";
        // true in JDK8
        System.out.println(s1 == s2);
    }

    public static void test_intern_2() {
        String s1 = new String("123") + new String("123");
        String s2 = "123123";
        s1.intern();
        // false in JDK8
        System.out.println(s1 == s2);
    }

    public static void test_intern_3() {
        String s1 = new String("123") + new String("123");
        String s2 = "123123";
        s1 = s1.intern();
        // true in JDK8
        System.out.println(s1 == s2);
    }
}
