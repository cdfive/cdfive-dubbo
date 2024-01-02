package com.cdfive.learn.javabasic;

import java.util.Arrays;

/**
 * @author cdfive
 */
public class StringSplitTest {

    public static void main(String[] args) {
        // 1
        System.out.println("1".split(",").length);
        // 1
        System.out.println("1,".split(",").length);
        // 2
        System.out.println("1,".split(",", 2).length);
        // 2
        System.out.println("1,2,,".split(",", 2).length);
        // 4
        System.out.println("1,2,,".split(",", -1).length);
        // 2
        System.out.println("1,2,,".split(",", 0).length);
        // 1
        System.out.println("1".split(",", 2).length);

        // [1]
        System.out.println(Arrays.asList("1-".split("-")));
        // [1, ]
        System.out.println(Arrays.asList("1-".split("-", -1)));
        // [1, ]
        System.out.println(Arrays.asList("1-".split("-", 2)));
        // [1, 2-3]
        System.out.println(Arrays.asList("1-2-3".split("-", 2)));
        // [1, 2-]
        System.out.println(Arrays.asList("1-2-".split("-", 2)));
        // [1, 2, ]
        System.out.println(Arrays.asList("1-2-".split("-", 3)));
        // [1, 2, ]
        System.out.println(Arrays.asList("1-2-".split("-", -1)));

        // [1, , 1]
        System.out.println(Arrays.asList("1--1".split("-")));
        // [1, -1]
        System.out.println(Arrays.asList("1--1".split("-", 2)));
        // 1
        System.out.println(Arrays.asList("1".split("-", 2).length));

        String[] tokens = "--".split("-");
        // true
        System.out.println(tokens.length == 0);
    }
}
