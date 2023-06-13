package com.cdfive.framework.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdfive
 */
public class CommonUtilTest {

    @Test
    public void testObjToMap() {
        Item item = new Item("test", 5, null);

        Map<String, Object> properties = new HashMap<>();
        properties.put("year", 2023);
        properties.put("colors", Arrays.asList("Red", "Yellow", "Green"));
        item.setProperties(properties);

        Map<String, Object> map = CommonUtil.objToMap(item);
        System.out.println(map);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class Item {

        private String name;

        private Integer quantity;

        private Map<String, Object> properties;
    }
}
