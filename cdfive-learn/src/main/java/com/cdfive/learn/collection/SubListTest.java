package com.cdfive.learn.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cdfive
 */
public class SubListTest {

    public static void main(String[] args) {
        List<Integer> list = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());

        System.out.println(list.subList(0, 1));

        // IndexOutOfBoundsException
        try {
            System.out.println(list.subList(3, 6));
        } catch (Exception e) {
            System.out.println(e instanceof IndexOutOfBoundsException);
            System.out.println(e.getMessage());
        }

        System.out.println(list.subList(3, 3));
        System.out.println(list.subList(3, 4));
        System.out.println(list.subList(3, 5));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        List<Product> products = new ArrayList<>();
        products.add(new Product("1001", "苹果"));
        products.add(new Product("1002", "梨子"));
        products.add(new Product("1003", "香蕉"));
        System.out.println(products);

        List<Product> subProducts = products.subList(2, products.size());
        System.out.println(subProducts);
        System.out.println(products);

        subProducts.clear();
        System.out.println(subProducts);
        System.out.println(products);
    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class Product {

        private String code;

        private String name;
    }
}
