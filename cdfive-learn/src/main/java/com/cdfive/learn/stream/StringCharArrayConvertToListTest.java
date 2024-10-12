package com.cdfive.learn.stream;

import com.google.common.base.Joiner;
import com.google.common.primitives.Chars;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class StringCharArrayConvertToListTest {

    public static void main(String[] args) {
        String str = "abc";

        char[] chars = str.toCharArray();
        List<char[]> invalidList = Arrays.asList(chars);
        System.out.println(invalidList);

        List<Character> validList = new ArrayList<>();
        for (char aChar : chars) {
            validList.add(aChar); // 写法1
//            validList.add((Character) aChar); // 写法2
//            validList.add(Character.valueOf(aChar)); // 写法3
        }
        System.out.println(validList);

        List<String> validStringList = new ArrayList<>();
        for (char aChar : chars) {
            validStringList.add(String.valueOf(aChar));
        }
        System.out.println(validStringList);

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        List<Character> characterList1 = Arrays.asList(str.split("")).stream().map(o -> o.charAt(0)).collect(Collectors.toList());
        List<Character> characterList2 = str.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        List<Character> characterList3 = Chars.asList(str.toCharArray());
        System.out.println(characterList1);
        System.out.println(characterList2);
        System.out.println(characterList3);

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        List<String> stringList1 = Arrays.stream(str.split("")).collect(Collectors.toList());
        List<String> stringList2 = str.chars().mapToObj(i -> String.valueOf((char) i)).collect(Collectors.toList());
        List<String> stringList3 = Chars.asList(str.toCharArray()).stream().map(o -> String.valueOf(o)).collect(Collectors.toList());
        System.out.println(stringList1);
        System.out.println(stringList2);
        System.out.println(stringList3);

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        List<String> tmpList1 = str.chars().mapToObj(i -> String.valueOf(i)).collect(Collectors.toList());
        List<String> tmpList2 = str.chars().mapToObj(i -> String.valueOf((char) i)).collect(Collectors.toList());
        // [97, 98, 99]
        System.out.println(tmpList1);
        // [a, b, c]
        System.out.println(tmpList2);

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        String line = "10001";
        Integer[] tmpArray1 = line.chars().mapToObj(o -> o).toArray(Integer[]::new);
        // 49,48,48,48,49
        System.out.println(Joiner.on(",").join(tmpArray1));
        Integer[] tmpArray2 = line.chars().mapToObj(o -> Integer.parseInt(String.valueOf((char) o))).toArray(Integer[]::new);
        // 1,0,0,0,1
        System.out.println(Joiner.on(",").join(tmpArray2));
    }
}
