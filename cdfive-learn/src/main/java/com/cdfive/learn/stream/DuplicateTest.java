package com.cdfive.learn.stream;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class DuplicateTest {

    public static void main(String[] args) {
        Item item1 = new Item("10001", "商品1");
        Item item2 = new Item("10002", "商品2");
        Item item3 = new Item("10001", "商品1");
        Item item4 = new Item("10004", "商品4");
        Item item5 = new Item("10003", "商品3");
        Item item6 = new Item("10004", "商品4");

        List<Item> list = Lists.newArrayList(item1, item2, item3, item4, item5, item6);

        // Find dulicate productCodes
        // [10001, 10004]
        List<String> duplicateProductCodes = list.stream().collect(Collectors.toMap(o -> o.getProductCode(), o -> 1, (o1, o2) -> o1 + o2, LinkedHashMap::new))
                .entrySet().stream().filter(o -> o.getValue() > 1).map(o -> o.getKey()).collect(Collectors.toList());
        System.out.println(duplicateProductCodes);
    }

    @AllArgsConstructor
    @Data
    private static class Item {

        private String productCode;

        private String productName;
    }
}
