package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

/**
 * @author cdfive
 */
public class CharIntegerConvertTest {

    public static void main(String[] args) {
        String str = "abc123";

        char c1 = str.charAt(0);
        // a
        System.out.println(c1);
        // 97
        System.out.println((int) c1);
        // 10
        System.out.println(Character.getNumericValue(c1));
        // java.lang.NumberFormatException
        try {
            System.out.println(Integer.valueOf(String.valueOf(c1)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        System.out.println(StringUtils.center("分隔符", 30, "-"));

        char c2 = str.charAt(3);
        // 1
        System.out.println(c2);
        // 49
        System.out.println((int) c2);
        // 1
        System.out.println(Character.getNumericValue(c2));
        // 1
        System.out.println(Integer.valueOf(String.valueOf(c2)));
    }
}
