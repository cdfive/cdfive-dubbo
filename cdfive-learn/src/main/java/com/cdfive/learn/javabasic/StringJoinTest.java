package com.cdfive.learn.javabasic;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author cdfive
 */
public class StringJoinTest {

    public static void main(String[] args) {
        System.out.println(String.join(",", Arrays.asList("a")));
        System.out.println(String.join(",", Arrays.asList("a", "b", "c")));
        System.out.println(String.join(",", Arrays.asList("")));
        System.out.println(String.join(",", Collections.emptyList()));
    }
}
