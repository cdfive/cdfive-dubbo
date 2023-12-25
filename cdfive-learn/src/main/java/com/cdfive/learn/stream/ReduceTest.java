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

        Integer sum3 = list.parallelStream().reduce(0, (a, b) -> a + b);
        System.out.println("sum3=" + sum3); // 55

        Integer sum4 = list.stream().reduce(100, (a, b) -> a + b);
        System.out.println("sum4=" + sum4);// 155

        Integer sum5 = list.parallelStream().reduce(100, (a, b) -> a + b);
        System.out.println("sum5=" + sum5);// 1055

        String str = list.stream().map(o -> String.valueOf(o)).reduce((a, b) -> a + "," + b).get();
        System.out.println("str=" + str);// 1,2,3,4,5,6,7,8,9,10
    }
}
