package com.cdfive.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cdfive.common.vo.JsonResult;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cdfive
 */
public class FastJsonUtilTest {

    @Test
    public void testJson2Map() {
        String json = "{\"name\":\"cdfive\",\"sex\":true,\"age\":18}";
        Map<String, String> map = FastJsonUtil.json2Map(json);
        System.out.println(map);
        System.out.println(map instanceof HashMap);

        Map<String, String> orderedMap = FastJsonUtil.json2MapOrdered(json);
        System.out.println(orderedMap);
        System.out.println(orderedMap instanceof LinkedHashMap);
    }

    @Test
    public void testJson2JsonResult() {
        JsonResult<Person> jsonResult = new JsonResult<>();
        Person person = new Person();
        person.setName("java");
        person.setSex(true);
        person.setAge(18);
        jsonResult.setData(person);
        jsonResult.setCode(JsonResult.SUCCESS_CODE);
        String json = FastJsonUtil.obj2Json(jsonResult);
        System.out.println(json);

        JsonResult<Person> result = FastJsonUtil.json2JsonResult(json, Person.class);
        System.out.println(result.getData() instanceof Person);
        System.out.println(result);

        result = JSONObject.parseObject(json, new TypeReference<JsonResult<Person>>(){}.getType());
        System.out.println(result.getData() instanceof Person);
        System.out.println(result);
    }

    @Data
    public static class Person {
        private String name;
        private Boolean sex;
        private Integer age;
    }
}
