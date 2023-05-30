package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class IntegerTest2 {

    public static void main(String[] args) {
        int num = 0xAA;
        // 170
        System.out.println(num);

        String a = "AA";
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            // 10
            System.out.println(Character.getNumericValue(c));
        }
    }
}
