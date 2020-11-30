package com.cdfive.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author cdfive
 */
public class JsonUtil {

    public static String objToJson(Object obj) {
        if (obj == null) {
            return null;
        }

        return JSON.toJSONString(obj);
    }

    public static <T> T jsonToObj(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static String obj2Json(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
    }

    public static <T> T json2Obj(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
