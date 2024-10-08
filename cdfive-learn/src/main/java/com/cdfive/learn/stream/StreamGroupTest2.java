package com.cdfive.learn.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class StreamGroupTest2 {

    public static void main(String[] args) {
        String str = "aabccaddcdegfegh";
        System.out.println(str);

        Map<String, List<String>> map1 = Arrays.stream(str.split("")).collect(Collectors.groupingBy(o -> o));
        System.out.println(map1);

        Map<String, Integer> map2 = map1.entrySet().stream().collect(Collectors.toMap(o -> o.getKey(), o -> o.getValue().size()));
        System.out.println(map2);

        Map<String, Long> map3 = Arrays.stream(str.split("")).collect(Collectors.groupingBy(o -> o, Collectors.counting()));
        System.out.println(map3);

        int min = map3.entrySet().stream().map(o -> o.getValue()).mapToInt(o -> o.intValue()).min().getAsInt();
        Set<String> set = map3.entrySet().stream().filter(o -> o.getValue().intValue() == min).map(o -> o.getKey()).collect(Collectors.toSet());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String s = String.valueOf(str.charAt(i));
            if (!set.contains(s)) {
                result.append(s);
            }
        }
        System.out.println(result.toString());
    }
}
