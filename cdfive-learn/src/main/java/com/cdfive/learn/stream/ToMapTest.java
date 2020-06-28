package com.cdfive.learn.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class ToMapTest {

    public static void main(String[] args) {
        Person p1 = new Person("aa", 1);
        Person p2 = new Person("bb", 2);
        Person p3 = new Person("cc", 1);
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        // Exception in thread "main" java.lang.IllegalStateException: Duplicate key ToMapTest.Person(name=aa, age=1)
//        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(o -> o.getAge(), o -> o));

        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(o -> o.getAge(), o -> o, (a, b) -> b));
        System.out.println(map);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class Person {
        private String name;

        private Integer age;
    }
}
