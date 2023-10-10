package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class StringReplaceTest {

    public static void main(String[] args) {
        String name = "123.ab.c3";
        String result;

        // Case1: !!!Pay attention to that it replaces all
        result = name.replace(".","。");
        // 123。ab。c3
        System.out.println(result);

        // Case2: Since name has no \. and replace nothing
        result = name.replace("\\.","。");
        // 123.ab.c3
        System.out.println(result);

        // Case3: !!!Pay attention to the first argument, it's a regex expression
        result = name.replaceAll(".","。");
        // 。。。。。。。。。
        System.out.println(result);

        // Case4: !!!Pay attention to it's equivalent to Case1
        result = name.replaceAll("\\.","。");
        // 123。ab。c3
        System.out.println(result);

        // Case5: 12。.ab.c。
        result = name.replaceAll("3","。");
        System.out.println(result);
    }
}
