package com.cdfive.learn.javabasic;

import java.io.InputStream;

/**
 * @author cdfive
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoaderTest obj = new ClassLoaderTest();
        InputStream test = obj.getClass().getClassLoader().getResourceAsStream("test");
        System.out.println();
    }
}
