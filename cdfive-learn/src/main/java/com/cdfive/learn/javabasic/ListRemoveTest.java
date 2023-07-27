package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cdfive
 */
public class ListRemoveTest {

    public static void main(String[] args) {
        try {
            remove1();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        remove2();

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        remove3();

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        remove4();

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        remove5();
    }

    public static void remove1() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list);

        int size = list.size();
        for (int i = 0; i < size; i++) {
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

        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            list.remove(item);
        }

        System.out.println(list);
    }

    public static void remove3() {
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

    public static void remove4() {
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

    public static void remove5() {
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
