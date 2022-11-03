package com.cdfive.learn.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author cdfive
 */
public class ReduceTest {

    public static void main(String[] args) {
        int sum1 = IntStream.rangeClosed(1, 10).reduce(Integer::sum).getAsInt();
        System.out.println("sum1=" + sum1); // 55

        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum2 = list.stream().reduce(0, (a, b) -> a + b);
        System.out.println("sum2=" + sum2); // 55
    }
}
