package com.cdfive.learn.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import lombok.Data;

/**
 * @author cdfive
 */
public class JacksonDemo2 {

    public static void main(String[] args) throws Exception {
        Product p = new Product();
        p.setName("test");

        Product2 p2 = new Product2();
        p2.setName("test");

        ObjectMapper objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // {"name":"test"}
        System.out.println(objectMapper.writeValueAsString(p));

        // {"name":"test","promotion":null}
        System.out.println(objectMapper.writeValueAsString(p2));
    }

    @Data
    private static class Product {

        private String name;

        private Promotion promotion;
    }

    @Data
    private static class Product2 {

        private String name;

        @JsonSerialize(nullsUsing = NullSerializer.class)
        private Promotion promotion;
    }

    @Data
    private static class Promotion {

        private String id;

        private Integer price;
    }
}
