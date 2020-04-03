package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class InstanceOfIsInstanceTest {

    public static void main(String[] args) {
        // true
        System.out.println("test" instanceof Object);
        // true
        System.out.println("test" instanceof String);

        // false
        System.out.println("test".getClass().isInstance(Object.class));
        // false
        System.out.println("test".getClass().isInstance(String.class));

        // true
        System.out.println("test".getClass().equals(String.class));
    }
}
