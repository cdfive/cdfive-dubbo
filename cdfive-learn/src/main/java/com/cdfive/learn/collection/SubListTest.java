package com.cdfive.learn.collection;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cdfive
 */
public class SubListTest {

    public static void main(String[] args) {
        List<Integer> list = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());

        System.out.println(list.subList(0, 1));

        // IndexOutOfBoundsException
        try {
            System.out.println(list.subList(3, 6));
        } catch (Exception e) {
            System.out.println(e instanceof IndexOutOfBoundsException);
            e.printStackTrace();
        }

        System.out.println(list.subList(3, 3));
        System.out.println(list.subList(3, 4));
        System.out.println(list.subList(3, 5));
    }
}
