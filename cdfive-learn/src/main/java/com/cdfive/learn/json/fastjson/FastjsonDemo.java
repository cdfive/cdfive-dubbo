package com.cdfive.learn.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cdfive
 */
public class FastjsonDemo {

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        String json = "{\"code\":200,\"msg\":\"success\",\"ts\":" + now;
        json += ",\"data\":{\"id\":100001,\"name\":\"keyboard\",\"createTime\":\"2020-06-04 11:11:11\"}";
        json += "}";

        System.out.println(json);

        // data in jsonResult is JSONObject type
        JsonResult jsonResult = JSON.parseObject(json, JsonResult.class);
        System.out.println(jsonResult);

        // ok
        JsonResult<Item> jsonResult2 = JSON.parseObject(json, new TypeReference<JsonResult<Item>>(){}.getType());
        System.out.println(jsonResult2);

        // null value
        Item item = new Item();
        item.setId(111L);
        System.out.println(JSON.toJSONString(item));
        System.out.println(JSON.toJSONString(item, SerializerFeature.WriteMapNullValue));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class JsonResult<T> implements Serializable {

        public static final Integer SUCCESS_CODE = 200;

        private Integer code;

        private String msg;

        private T data;

        private Long ts;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Item {

        private Long id;

        private Integer a;

        private int b;

        private String name;

        private Date createTime;
    }
}
