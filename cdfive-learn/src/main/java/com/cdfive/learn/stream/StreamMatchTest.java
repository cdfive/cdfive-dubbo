package com.cdfive.learn.stream;

import java.util.ArrayList;
import java.util.List;

public class StreamMatchTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        // true, If the stream is empty then {@code true} is returned and the predicate is not evaluated.
        System.out.println(list.stream().allMatch(o -> o.contains("a")));
        // false
        System.out.println(list.stream().anyMatch(o -> o.contains("a")));
        // true
        System.out.println(list.stream().noneMatch(o -> o.contains("a")));
    }
}
