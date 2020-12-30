package com.cdfive.learn.javabasic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cdfive
 */
public class ListRemoveTest {

    public static void main(String[] args) {
        remove2();
    }

    public static void remove1() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list);

        for (int i = list.size() - 1; i >= 0; i--) {
            String item = list.get(i);
            list.remove(item);
        }

        System.out.println(list);
    }

    public static void remove2() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list);

        for (int i = list.size() - 1; i >= 0; i--) {
            if (i == 1) {
                list.remove(i);
            }
        }

        System.out.println(list);
    }

    public static void remove3() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list);

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if ("1".equals(item)) {
                iterator.remove();
            }
        }

        System.out.println(list);
    }
}
