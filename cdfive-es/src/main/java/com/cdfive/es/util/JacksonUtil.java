package com.cdfive.es.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.StringUtils;

/**
 * @author cdfive
 */
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

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
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazzItem, classes);
        try {
            return OBJECT_MAPPER.readValue(content, javaType);
        } catch (Exception e) {
            throw new RuntimeException("JacksonUtil#jsonToObj error", e);
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
