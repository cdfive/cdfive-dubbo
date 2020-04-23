package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class StringNullTest {
    public static void main(String[] args) {
//        String x = null;
        byte[] x = null;
        try {
            String a = new String(x);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String b = new String(new byte[0]);
        System.out.println(b);
    }
}
