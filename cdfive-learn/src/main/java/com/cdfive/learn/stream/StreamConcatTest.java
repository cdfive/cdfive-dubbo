package com.cdfive.learn.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cdfive
 */
public class StreamConcatTest {

    public static void main(String[] args) {
        List<Item> list1 = new ArrayList<Item>() {{
            add(Item.of(1, "a"));
            add(Item.of(2, "b"));
            add(Item.of(3, "c"));
        }};

        List<Item> list2 = new ArrayList<Item>() {{
            add(Item.of(3, "c"));
            add(Item.of(4, "d"));
            add(Item.of(5, "e"));
        }};

        List<Integer> ids = Stream.concat(list1.stream(), Optional.ofNullable(list2).map(o -> o.stream()).orElse(Stream.empty()))
                .map(o -> o.getId()).distinct().collect(Collectors.toList());
        // [1, 2, 3, 4, 5]
        System.out.println(ids);

        System.out.println(StringUtils.center("分隔符", 50, "-"));

        List<Item> list = new ArrayList<Item>() {{
            add(Item.of(1, "aa", "aa"));
            add(Item.of(2, "aa", "dd"));
            add(Item.of(2, "bb", "cc"));
            add(Item.of(2, "ee", null));
            add(Item.of(2, null, "ff"));
            add(Item.of(2, "gg", "aa"));
        }};

        List<String> names = Stream.concat(list.stream().filter(o -> o.getName() != null).map(o -> o.getName())
                , list.stream().filter(o -> o.getNickName() != null).map(o -> o.getNickName())).distinct().collect(Collectors.toList());
        // [aa, bb, ee, gg, dd, cc, ff]
        System.out.println(names);
    }

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Item {

        public static Item of(Integer id, String name) {
            return new Item(id, name, null);
        }

        private Integer id;

        private String name;

        private String nickName;
    }
}
