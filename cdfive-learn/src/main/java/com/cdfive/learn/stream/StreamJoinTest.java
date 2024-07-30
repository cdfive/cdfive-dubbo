package com.cdfive.learn.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class StreamJoinTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");

        String str1 = list.stream().collect(Collectors.joining());
        // abc
        System.out.println(str1);

        String str2 = list.stream().collect(Collectors.joining(","));
        // a,b,c
        System.out.println(str2);
    }
}
