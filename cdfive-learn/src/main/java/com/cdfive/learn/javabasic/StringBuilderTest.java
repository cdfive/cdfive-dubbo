package com.cdfive.learn.javabasic;

/**
 * @author cdfive
 */
public class StringBuilderTest {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
//        sb.append(null).append("111");// Compile error, ambiguous method call
        Integer a = null;
        sb.append(a).append("111");
        System.out.println(sb);
    }
}
