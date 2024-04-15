package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class LongTest {

    public static void main(String[] args) {
        // -9223372036854775808
        System.out.println(Long.MIN_VALUE);
        // 20
        System.out.println(String.valueOf(Long.MIN_VALUE).length());
        // 9223372036854775807
        System.out.println(Long.MAX_VALUE);
        // 19
        System.out.println(String.valueOf(Long.MAX_VALUE).length());

//        Long tooLarge = 12738430987973591044L; // Long number too large
        try {
            Long tooLarge = Long.parseLong("12738430987973591044");
        } catch (NumberFormatException e) {
            // java.lang.NumberFormatException: For input string: "12738430987973591044"
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }

        String s = "343651486535913456";
        // 343651486535913456
        System.out.println(Long.valueOf(s));
        // 343651486535913456
        System.out.println(Long.valueOf(s + "1"));
        // 3436514865359134569;
        System.out.println(Long.valueOf(s + "9"));

        // NFE,Long类型正数不能大于19位
        try {
            System.out.println(Long.valueOf(s + "11"));
        } catch (NumberFormatException e) {
            System.out.println("NFE");
        }
        // NFE,Long类型正数18位但超过了最大值
        s = "934189997037207552";
        try {
            System.out.println(Long.valueOf(s + "1"));
        } catch (NumberFormatException e) {
            System.out.println("NFE");
        }

        // 18位数字时最前面可以+1,这时没有超过最大值溢出
        // 1934189997037207552
        s = "934189997037207552";
        System.out.println(Long.valueOf("1" + s));
    }
}
