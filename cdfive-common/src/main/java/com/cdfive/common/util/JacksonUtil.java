package com.cdfive.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Map;

/**
 * @author cdfive
 */
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new GuavaModule())
//                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || jsonStr.equals("{}")) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JacksonUtil#jsonToObj error,jsonStr=" + jsonStr, e);
        }
    }

    @SuppressWarnings({"rawtypes", "deprecation"})
    public static <T> T jsonToObj(String jsonStr, Class<T> clazzItem, Class... classes) {
        if (StringUtil.isBlank(jsonStr)) {
            return null;
        }

        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazzItem, classes);
        try {
            return OBJECT_MAPPER.readValue(jsonStr, javaType);
        } catch (Exception e) {
            throw new RuntimeException("JacksonUtil#jsonToObj error,jsonStr=" + jsonStr, e);
        }
    }

    public static <K, V> Map<K, V> jsonToMap(String jsonStr, Class<K> keyClass, Class<V> valueClass) {

        try {
            return OBJECT_MAPPER.readValue(jsonStr, OBJECT_MAPPER.getTypeFactory().constructParametricType(Map.class, new Class[]{keyClass, valueClass}));
        } catch (IOException e) {
            throw new RuntimeException("JacksonUtil#jsonToMap error,jsonStr=" + jsonStr, e);
        }
    }

    public static String objToJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JacksonUtil#objToJson error", e);
        }
    }
}
