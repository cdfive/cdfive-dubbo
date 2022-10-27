package com.cdfive.learn.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    }

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Item {
        private Integer id;

        private String name;
    }
}
