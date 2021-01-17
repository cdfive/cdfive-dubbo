package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class StringReplaceTest {

    public static void main(String[] args) {
        String name = "123.ab.c";
        String result;

        result = name.replace(".","。");
        // 123。ab。c
        System.out.println(result);

        // !!!Pay attention to the first argument, it's a regex expression.
        result = name.replaceAll(".","。");
        // 。。。。。。。。
        System.out.println(result);
    }
}
