package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class ClassForNameTest {

    public static void main(String[] args) throws ClassNotFoundException {
        // print => StaticClass's static block
        Class.forName("com.cdfive.learn.javabasic.StaticClassTest");

        // won't print => StaticClass's static block
//        Class.forName("com.cdfive.learn.javabasic.StaticClassTest", false, Thread.currentThread().getContextClassLoader());
        System.out.println("ClassForNameTest done");
    }
}
