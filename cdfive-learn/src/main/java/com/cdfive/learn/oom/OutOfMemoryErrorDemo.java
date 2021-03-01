package com.cdfive.learn.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * java.lang.OutOfMemoryError: Java heap space
 * Add VM argument: -Xms1m -Xmx1m
 *
 * @author cdfive
 */
public class OutOfMemoryErrorDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000_000; i++) {
            list.add(new String("test"));
        }
        System.out.println("done");
    }
}
