package com.cdfive.learn.datastructure.map;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author cdfive
 */
public class MapKeySetRemoveTest {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        System.out.println(map.size());
        System.out.println(map);

        printSeperator();

        Set<String> keys = map.keySet();
        System.out.println(keys.size());
        Iterator<String> iterator = keys.iterator();
        // java.lang.IllegalStateException
//        iterator.remove();
        iterator.next();
        iterator.remove();
        System.out.println(keys.size());

        printSeperator();

        System.out.println(map.size());
        System.out.println(map);
    }

    private static void printSeperator() {
        System.out.println(StringUtils.center("分隔线", 50, "-"));
    }
}
