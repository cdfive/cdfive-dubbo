package com.cdfive.learn.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Simple learn demo for using Jackson
 *
 * @author cdfive
 */
public class JacksonDemo {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        demo1();
        demo2();
    }

    public static void demo1() throws JsonProcessingException {
        Person p = new Person();
        p.setName("zhangsan");
        p.setAge(28);
        p.setPhone("13611111314");

        String json = objectMapper.writeValueAsString(p);
        System.out.println(json);
    }

    public static void demo2() throws JsonProcessingException, IOException {
        List<Person> list = new ArrayList<>();

        Person p1 = new Person();
        p1.setName("111");
        p1.setAge(18);
        p1.setPhone("13800001111");
        list.add(p1);

        Person p2 = new Person();
        p2.setName("222");
        p2.setAge(30);
        p2.setPhone("13900002222");
        list.add(p2);

        String json = objectMapper.writeValueAsString(list);
        System.out.println(json);

        List list2 = objectMapper.readValue(json, List.class);
        System.out.println(list2.size());
        System.out.println(list2.get(0) instanceof LinkedHashMap);

        List<Person> list3 = objectMapper.readValue(json, new TypeReference<List<Person>>() {});
        System.out.println(list3.size());
        System.out.println(list3.get(0) instanceof Person);
    }

    @Data
    private static class Person {
        private String name;

        private Integer age;

        private String phone;
    }
}
