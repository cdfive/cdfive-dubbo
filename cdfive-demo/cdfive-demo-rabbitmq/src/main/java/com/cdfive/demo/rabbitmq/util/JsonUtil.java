package com.cdfive.demo.rabbitmq.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cdfive
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || jsonStr.equals("{}")) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JacksonUtil#jsonToObj error", e);
        }
    }

    @SuppressWarnings({"rawtypes", "deprecation"})
    public static <T> T jsonToObj(String content, Class<T> clazzItem, Class... classes) {
        if (content == null) {
            return null;
        }

        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazzItem, classes);
        try {
            return OBJECT_MAPPER.readValue(content, javaType);
        } catch (Exception e) {
            throw new RuntimeException("JsonUtil#jsonToObj error", e);
        }
    }

    public static String objToJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JsonUtil#objToJson error", e);
        }
    }
}
