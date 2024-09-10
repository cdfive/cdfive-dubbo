package com.cdfive.demo.mybatis.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
    }


    public static String objToStr(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T strToObj(String str, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String controllerArgsToStr(Object... args) {
        if (args == null || args.length == 0) {
            return null;
        }

        if (args.length == 1) {
            Object arg = args[0];
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                return null;
            }

            return JsonUtil.objToStr(arg);
        }

        if (args instanceof Object[]) {
            if (args.length == 0) {
                return null;
            }

            List<Object> reqList = Arrays.stream(args).collect(Collectors.toList());
            reqList.removeIf(o -> o instanceof HttpServletRequest || o instanceof HttpServletResponse);
            return JsonUtil.objToStr(reqList);
        }


        return JsonUtil.objToStr(args);
    }
}
