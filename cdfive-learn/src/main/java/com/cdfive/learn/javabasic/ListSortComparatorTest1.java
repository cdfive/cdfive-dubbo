package com.cdfive.learn.javabasic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author cdfive
 */
public class ListSortComparatorTest1 {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<Product>();

        Product p1 = new Product("1001", 3);
        Product p2 = new Product("1002", 2);
        Product p3 = new Product("1003", 5);
        Product p4 = new Product("1004", null);
        Product p5 = new Product("1005", 1);
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);

        products.forEach(p -> System.out.println(p.getCode() + "->" + p.getQuantity()));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        // NPE1
//        products.sort((a, b) -> a.getQuantity().compareTo(b.getQuantity()));

        // NPE2
//        products.sort(Comparator.nullsLast((a, b) -> a.getQuantity().compareTo(b.getQuantity())));

        // NPE3
//        products.sort(Comparator.comparingInt((Product a) -> a.getQuantity()));

        // OK1
        /*products.sort((product1, product2) -> {
            Integer q1 = product1.getQuantity();
            Integer q2 = product2.getQuantity();

            if (q1 == null) {
                if (q2 == null) {
                    return 0;
                }

                return 1;
            }

            if (q2 == null) {
                return -1;
            }

            return q1.compareTo(q2);
        });*/

        // OK2
//        products.sort(Comparator.comparing(a -> a.getQuantity(), Comparator.nullsLast(Comparator.naturalOrder())));

        // OK3
//        products.sort(Comparator.comparing(a -> a.getQuantity(), Comparator.nullsLast((a, b) -> Integer.compare(a, b))));

        // OK4
//        products.sort(Comparator.comparing(a -> a.getQuantity(), Comparator.nullsLast((a, b) -> a.compareTo(b))));

        products.forEach(p -> System.out.println(p.getCode() + "->" + p.getQuantity()));
    }

    @AllArgsConstructor
    @Data
    private static class Product {

        private String code;

        private Integer quantity;
    }
}
