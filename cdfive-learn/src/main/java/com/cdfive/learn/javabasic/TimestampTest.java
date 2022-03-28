package com.cdfive.learn.javabasic;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class TimestampTest {

    public static void main(String[] args) throws Exception {
        // usually yyyy-MM-dd HH:mm:ss.SSS, but maybe yyyy-MM-dd HH:mm:ss.SS or yyyy-MM-dd HH:mm:ss.S
        System.out.println(new Timestamp(System.currentTimeMillis()));
        System.out.println("------------------------");

        // test print many times
        for (int i = 0; i < 20; i++) {
            sleepAndPrintTime();
        }
        System.out.println("------------------------");

        // test custorm format
        String testTime = "2020-10-17 17:57:21.73";
        Date date = DateUtils.parseDate(testTime, "yyyy-MM-dd HH:mm:ss.SS");
        String dateStr = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(dateStr); // 2020-10-17 17:57:21.073
        System.out.println(date.getTime()); // 1602928641073
        System.out.println("------------------------");

        // pay attention to this
        Timestamp timestamp0 = new Timestamp(0);
        // 1970-01-01 08:00:00.0
        System.out.println(timestamp0);
        // test json convert using fastjson
        String json = "{\"updateTime\":0}";
        Item item = JSONObject.parseObject(json, Item.class);
        // {"updateTime":0}
        System.out.println(JSONObject.toJSONString(item));
        // 1970-01-01 08:00:00.0
        System.out.println(item.updateTime);

        System.out.println("------------------------");
        // 1647927852718 (2022-03-22)
        System.out.println(System.currentTimeMillis());
        // 13
        System.out.println(String.valueOf(System.currentTimeMillis()).length());
    }

    private static void sleepAndPrintTime() throws Exception {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }

    @Data
    private static class Item implements Serializable {

        private Timestamp updateTime;
    }
}
