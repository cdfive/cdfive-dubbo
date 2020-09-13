package com.cdfive.common.util;

import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author cdfive
 */
public class GenericClassUtil {

    public static Class getGenericTypeFromSuperClass(Class targetClass, int index) {
        Type superType = targetClass.getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superType;
            return getActualTypeArgumentClass(parameterizedType, index);
        } else {
            return Object.class;
        }
    }

    private static Class getActualTypeArgumentClass(ParameterizedType parameterizedType, int index) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Assert.isTrue(actualTypeArguments.length > index, "Number of type arguments must be great or equals " + index);
        return (Class) actualTypeArguments[index];
    }
}
