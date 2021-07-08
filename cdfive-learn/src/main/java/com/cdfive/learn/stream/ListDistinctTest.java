package com.cdfive.learn.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class ListDistinctTest {

    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(Book.of(1L, "Thinking in Java", "2021-06-29 17:13:14"));
        books.add(Book.of(2L, "Hibernate in action", "2021-06-29 18:13:14"));
        books.add(Book.of(3L, "Thinking in Java", "2021-06-29 19:13:14"));
        // 原始数据打印
        System.out.println(books);

        // 虽然去重了,但是顺序变了
        List<Book> distinctNameBooks1 = books.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName()))), ArrayList::new));
        System.out.println(distinctNameBooks1);

        // 去重且保持顺序,但是内部创建了HashMap,多了一些内存占用
        List<Book> distinctNameBooks2 = books.stream().filter(distinctByKey(o -> o.getName())).collect(Collectors.toList());
        System.out.println(distinctNameBooks2);

        // 去重且保持顺序,创建了HashMap,多了一些内存占用
        Map<Object, Boolean> map = new HashMap<>();
        List<Book> distinctNameBooks3 = books.stream().filter(i -> map.putIfAbsent(i.getName(), Boolean.TRUE) == null).collect(Collectors.toList());
        System.out.println(distinctNameBooks3);
    }

    // 封装去重方法
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new HashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class Book {

        public static Book of(Long id, String name, String createTime) {
            return new Book(id, name, Date.from(LocalDateTime.parse(createTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant()));
        }

        private Long id;

        private String name;

        private Date createTime;
    }
}
