package com.cdfive.learn.javabasic;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class ReflectTest {

    public static void main(String[] args) {
        List<Field> nonStaticFields = new ArrayList<>();
        Class<?> clazz = DepotProductEntity.class;
        do {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                nonStaticFields.addAll(Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).collect(Collectors.toList()));
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);

        System.out.println(nonStaticFields.size());

        for (Field field : nonStaticFields) {
            Autowired autowired = field.getAnnotation(Autowired.class); // ok
            Getter getter = field.getAnnotation(Getter.class); // null
            System.out.println(field.getName() + "=>" + autowired + "," + getter);
        }
    }

    @Data
    private static class ProductEntity {

        @Autowired
        @Getter
        private String id;

        private String productCode;
    }

    @Data
    private static class DepotProductEntity extends ProductEntity {

        @Autowired
        @Getter
        private String id;

        private String depotCode;
    }
}
