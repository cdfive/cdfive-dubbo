package com.cdfive.learn.javabasic;

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
    }
}
