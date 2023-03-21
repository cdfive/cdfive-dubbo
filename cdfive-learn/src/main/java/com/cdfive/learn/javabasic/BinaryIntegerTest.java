package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

/**
 * @author cdfive
 */
public class BinaryIntegerTest {

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(7)); // 111

        printSeparateLine();

        int i = 7;
        int ii = 0b111;
        System.out.println(i == ii); // true

        printSeparateLine();

        System.out.println((ii & 0b001) == 0b001); // true
        System.out.println((ii & 0b010) == 0b010);// true
        System.out.println((ii & 0b100) == 0b100);// true

        printSeparateLine();

        System.out.println((i & (1 << 0)) == (1 << 0)); // true
        System.out.println((i & (1 << 1)) == (1 << 1)); // true
        System.out.println((i & (1 << 2)) == (1 << 2)); // true

        printSeparateLine();

        System.out.println((1 & 0b111) == 1); // true
        System.out.println((2 & 0b111) == 2); // true
        System.out.println((4 & 0b111) == 4); // true

        printSeparateLine();

        System.out.println((6 & (1 << 0)) != 0); // false
        System.out.println((6 & (1 << 1)) != 0); // true
        System.out.println((6 & (1 << 2)) != 0); // true

        printSeparateLine();

        int x = 0;
        System.out.println((6 & (1 << x++)) != 0); // false
        System.out.println((6 & (1 << x++)) != 0); // true
        System.out.println((6 & (1 << x++)) != 0); // true
    }

    private static void printSeparateLine() {
        System.out.println(StringUtils.center("分隔线", 50, "-"));
    }
}
