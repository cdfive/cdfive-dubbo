package com.cdfive.learn.collection;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author cdfive
 */
public class DifferentMapTest {

    public static void main(String[] args) {
        testMap(new HashMap<>());

        testMap(new TreeMap<>());// new TreeMap<>((a, b) -> a.compareTo(b))

        testMap(new LinkedHashMap<>());
    }

    public static void testMap(Map<Integer, String> map) {
        System.out.println(StringUtils.center(map.getClass().getName(), 30, "-"));
        map.put(2, "a");
        map.put(3, "b");
        map.put(1, "c");
        map.put(12, "qq");
        map.put(18, "ww");
        map.put(15, "ee");
        System.out.println(map.keySet());
        System.out.println(map.values());
    }
}
