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
public class ListSortComparatorTest2 {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<Product>();

        Product p1 = new Product("1001", new Product.Sale(3));
        Product p2 = new Product("1002", new Product.Sale(2));
        Product p3 = new Product("1003", new Product.Sale(null));
        Product p4 = new Product("1004", null);
        Product p5 = new Product("1005", new Product.Sale(5));
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);

        products.forEach(p -> System.out.println(p.getCode() + "->" + Optional.ofNullable(p.getSale()).map(o -> o.getQuantity()).orElse(null)));

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        // NPE
//        products.sort(Comparator.comparing(a -> a.getSale(), Comparator.nullsLast((a, b) -> Integer.compare(a.getQuantity(), b.getQuantity()))));

        // OK1
//        products.sort(Comparator.comparing(a -> a.getSale()
//                , Comparator.nullsLast(Comparator.comparing(s -> s.getQuantity()
//                        , Comparator.nullsLast((a, b) -> a.compareTo(b))))));

        // OK2
        products.sort(Comparator.comparing(a -> Optional.ofNullable(a.getSale()).map(Product.Sale::getQuantity).orElse(null)
                , Comparator.nullsLast((a, b) -> a.compareTo(b))));

        // OK3
//        products.sort(Comparator.comparing(a -> Optional.ofNullable(a).map(Product::getSale).map(Product.Sale::getQuantity).orElse(null)
//                , Comparator.nullsLast((a, b) -> a.compareTo(b))));

        products.forEach(p -> System.out.println(p.getCode() + "->" + Optional.ofNullable(p.getSale()).map(o -> o.getQuantity()).orElse(null)));
    }

    @AllArgsConstructor
    @Data
    private static class Product {

        private String code;

        private Sale sale;

        @AllArgsConstructor
        @Data
        private static class Sale {
            private Integer quantity;
        }
    }
}
