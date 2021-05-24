package com.cdfive.learn.datastructure.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class SortMapByValueDemo {

    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("1", 2);
        map.put("2", 3);
        map.put("3", 1);
        map.put("4", 5);
        map.put("5", 5);
        map.put("6", 4);

        // {1=2, 2=3, 3=1, 4=5, 5=5, 6=4}
        System.out.println(map);

        // {3=1, 1=2, 2=3, 6=4, 4=5, 5=5}
        HashMap<String, Integer> sortMap1 = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(o -> o.getKey(), o -> o.getValue(), (o1, o2) -> o2, LinkedHashMap::new));
        System.out.println(sortMap1);

        Map<String, Integer> sortMap2 = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(o -> sortMap2.put(o.getKey(), o.getValue()));
        // {3=1, 1=2, 2=3, 6=4, 4=5, 5=5}
        System.out.println(sortMap2);

        List<String> list = map.keySet().stream().collect(Collectors.toList());
        // [1, 2, 3, 4, 5, 6]
        System.out.println(list);

        List<String> sortList = map.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(o -> o.getKey()).collect(Collectors.toList());
        // [3, 1, 2, 6, 4, 5]
        System.out.println(sortList);
    }
}
