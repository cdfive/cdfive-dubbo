package com.cdfive.learn.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class StreamFlatMapTest {

    public static void main(String[] args) {
        Map<String, List<Product>> map = new HashMap<>();

        List<Product> listA = new ArrayList<>();
        map.put("aa", listA);
        listA.add(Product.of("1001", "apple"));
        listA.add(Product.of("1002", "banana"));

        List<Product> listB = new ArrayList<>();
        map.put("bb", listB);
        listB.add(Product.of("1002", "banana"));
        listB.add(Product.of("1003", "pear"));

        List<Product> result = map.entrySet().stream().flatMap(k -> k.getValue().stream()).distinct().collect(Collectors.toList());
        // [Product{productCode='1001', productName='apple'}, Product{productCode='1002', productName='banana'}, Product{productCode='1003', productName='pear'}]
        System.out.println(result);
    }

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Product {

        private String productCode;

        private String productName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Objects.equals(productCode, product.productCode) &&
                    Objects.equals(productName, product.productName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productCode, productName);
        }

        @Override
        public String toString() {
            return "Product{" +
                    "productCode='" + productCode + '\'' +
                    ", productName='" + productName + '\'' +
                    '}';
        }
    }
}
