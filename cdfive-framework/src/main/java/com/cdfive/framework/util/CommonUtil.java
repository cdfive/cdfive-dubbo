package com.cdfive.framework.util;

import org.springframework.util.ReflectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class CommonUtil {

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static Map<String, Object> objToMap(Object obj) {
        List<Field> nonStaticFields = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                nonStaticFields.addAll(Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).collect(Collectors.toList()));
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return nonStaticFields.stream()
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .collect(LinkedHashMap::new, (m, f) -> {
                    ReflectionUtils.makeAccessible(f);
                    m.put(f.getName(), ReflectionUtils.getField(f, obj));
                }, LinkedHashMap::putAll);
    }
}
