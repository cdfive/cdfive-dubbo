package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class ClassForNameTest {

    public static void main(String[] args) throws ClassNotFoundException {
//        Class.forName("com.cdfive.learn.javabasic.StaticClass");
        Class.forName("com.cdfive.learn.javabasic.StaticClassTest", false, Thread.currentThread().getContextClassLoader());
        System.out.println("ClassForNameTest done");
    }
}
