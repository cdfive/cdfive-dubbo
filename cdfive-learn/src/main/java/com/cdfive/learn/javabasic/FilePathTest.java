package com.cdfive.learn.javabasic;

import java.io.File;

/**
 * @author cdfive
 */
public class FilePathTest {

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(new File("").getAbsolutePath());
        System.out.println(new File(".").getAbsolutePath());
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println(FilePathTest.class.getClassLoader().getResource(""));
        System.out.println(FilePathTest.class.getResource(""));
        System.out.println(FilePathTest.class.getResource("").getPath());
        System.out.println(FilePathTest.class.getClassLoader().getResource("/"));
        System.out.println(new File(".").getAbsolutePath());

    }
}
