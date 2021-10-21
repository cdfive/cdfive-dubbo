package com.cdfive.learn.javabasic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author cdfive
 */
public class SpecialNPETest {

    public static void main(String[] args) {
        // Basic test
        basic();

        // OK
        case1();

        // NPE
        case2();

        // NPE
        case3();

        // OK
        case4();

        // OK
        case5();

        // OK
        case6();
    }

    private static void basic() {
        System.out.println("basic start");
        // OK
        Integer a = (Integer) null;
        // NPE
        try {
            int b = (Integer) null;
        } catch (Exception e) {
            System.out.println("exception=" + e.getClass().getName() + ",msg=" + e.getMessage());
        }
        System.out.println();
        System.out.println("basic end");
    }

    private static void case1() {
        System.out.println("case1 start");
        try {
            Item item = new Item();
            item.setId(1);
            item.setQuantity(5);

            // OK
            Integer result = item != null ? item.getQuantity() : 0;
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("case1 error,exception=" + e.getClass().getName() + ",msg=" + e.getMessage());
        }
        System.out.println("case1 end");
        System.out.println();
    }

    private static void case2() {
        System.out.println("case2 start");
        try {
            Item item = new Item();
            item.setId(1);

            // NPE
            Integer result = item != null ? item.getQuantity() : 0;
        } catch (Exception e) {
            System.out.println("case2 error,exception=" + e.getClass().getName() + ",msg=" + e.getMessage());
        }
        System.out.println("case2 end");
        System.out.println();
    }

    private static void case3() {
        System.out.println("case3 start");
        try {
            Item item = new Item();
            item.setId(1);

            // NPE
            int result = item != null ? item.getQuantity() : 0;
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("case3 error,exception=" + e.getClass().getName() + ",msg=" + e.getMessage());
        }
        System.out.println("case3 end");
        System.out.println();
    }

    private static void case4() {
        System.out.println("case4 start");
        try {
            Item item = new Item();
            item.setId(1);

            // OK
            Integer result;
            if (item != null) {
                result = item.getQuantity();
            } else {
                result = 0;
            }
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("case4 error,exception=" + e.getClass().getName() + ",msg=" + e.getMessage());
        }
        System.out.println("case4 end");
        System.out.println();
    }

    private static void case5() {
        System.out.println("case5 start");
        try {
            Item item = new Item();
            item.setId(1);

            // OK
            Integer result = item != null ? item.getQuantity() : Integer.valueOf(0);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("case5 error,exception=" + e.getClass().getName() + ",msg=" + e.getMessage());
        }
        System.out.println("case5 end");
        System.out.println();
    }

    private static void case6() {
        System.out.println("case6 start");
        try {
            Item item = new Item();
            item.setId(1);

            // OK
            Integer result = Optional.ofNullable(item).map(o -> o.getQuantity()).orElse(null);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("case6 error,exception=" + e.getClass().getName() + ",msg=" + e.getMessage());
        }
        System.out.println("case6 end");
        System.out.println();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class Item implements Serializable {

        private Integer id;

        private Integer quantity;
    }
}
