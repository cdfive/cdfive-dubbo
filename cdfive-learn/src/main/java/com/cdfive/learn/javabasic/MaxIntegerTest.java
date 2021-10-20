package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class MaxIntegerTest {

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE); // 2147483647
        System.out.println(Integer.MAX_VALUE + 1); // -2147483648

        System.out.println(Long.MAX_VALUE);// 9223372036854775807
        System.out.println(Long.MAX_VALUE + 1);// -9223372036854775808

        // java.lang.NumberFormatException: For input string: "21474836478"
        try {
            Integer i = Integer.parseInt("21474836478");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // java.lang.NumberFormatException: For input string: "3000000000"
        try {
            Integer.valueOf("3000000000");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Integer pageNum = 2147483647;
        System.out.println((pageNum - 1) * 10); // -20
    }
}
