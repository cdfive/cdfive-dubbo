package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class IntegerTest {
    public static void main(String[] args) {
        Integer a = 12300;
        System.out.println(a / 100.0);// 123.0
        a = 1230;
        System.out.println(a / 100.0);// 12.3
        a = 12310;
        System.out.println(a / 100.0);// 123.1
        a = 12315;
        System.out.println(a / 100.0);// 123.15

        System.out.println(Integer.MIN_VALUE); // -2147483648
        System.out.println(Integer.MAX_VALUE); // 2147483647

        System.out.println(Integer.MAX_VALUE + 1);
        System.out.println((Integer.MAX_VALUE + 1) == Integer.MIN_VALUE);

//        Integer i = 11456725200; // Compile error: Integer number too large
        // Exception in thread "main" java.lang.NumberFormatException: For input string: "11456725200"
        //	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
//        Integer ii = Integer.parseInt(String.valueOf("11456725200"));
//        System.out.println(ii);

//        Exception in thread "main" java.lang.NumberFormatException: null
//        at java.lang.Integer.parseInt(Integer.java:542)
//        at java.lang.Integer.valueOf(Integer.java:766)
//        System.out.println(Integer.valueOf(null));

        // Infinite loop
        /*
        int i = 2147483646;
        while (true) {
            System.out.println(i++);
        }
        */
    }
}
