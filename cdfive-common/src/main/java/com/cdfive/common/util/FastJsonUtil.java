package com.cdfive.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cdfive.common.vo.JsonResult;

import java.util.Map;

/**
 * @author cdfive
 */
public class FastJsonUtil {

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

//    public static <T> JsonResult<T> json2JsonResult(String json) {
////        return JSONObject.parseObject(json, new TypeReference<JsonResult<T>>(){});
//        return JSONObject.parseObject(json, new TypeReference<JsonResult<T>>(){}.getType());
//    }

    public static <T> JsonResult<T> json2JsonResult(String json, Class<T> clazz) {

        return JSONObject.parseObject(json, new TypeReference<JsonResult<T>>(clazz) {});
    }

    public static Map<String, String> json2Map(String json) {
        return JSON.parseObject(json, new TypeReference<Map<String, String>>(){}.getType());
    }

    public static Map<String, String> json2MapOrdered(String json) {
        return JSON.parseObject(json, new TypeReference<Map<String, String>>(){}.getType(), Feature.OrderedField);
    }
}
