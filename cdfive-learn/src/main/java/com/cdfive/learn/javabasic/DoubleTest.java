package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class DoubleTest {

    public static void main(String[] args) {
        // 0.30000000000000004
        System.out.println(0.1 * 3);;// 0.30000000000000004

        double d = 0.1;
        System.out.println(d * 3);// 0.30000000000000004
        System.out.println(d * 0.3);// 0.03

        System.out.println((int) d);// 0
        System.out.println((int) (d * 3));// 0
        System.out.println((int) (d * 9));// 0
        System.out.println((int) (d * 11));// 1
        System.out.println((int) (d * 0.5));// 0
        System.out.println((int) d * 0.5);// 0.0

        System.out.println(0.1 * 3 == 0.3); // false
        System.out.println(0.1F * 3 == 0.3); // false
        System.out.println(0.1F * 3 == 0.3F); // true
        System.out.println(0.1D * 3 == 0.3D); // false
        System.out.println(0.1 * 3 == 0.3D); // false
    }
}
