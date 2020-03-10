package com.cdfive.common.util;

import com.alibaba.fastjson.JSON;

/**
 * @author cdfive
 */
public class JsonUtil {

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }

        return JSON.toJSONString(obj);
    }

    public static <T> Object fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
