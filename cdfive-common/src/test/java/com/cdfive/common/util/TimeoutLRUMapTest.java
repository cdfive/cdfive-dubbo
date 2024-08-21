package com.cdfive.common.util;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class TimeoutLRUMapTest {

    @Test
    public void test() throws Exception {
        TimeoutLRUMap<String, String> map = new TimeoutLRUMap<String, String>(5, TimeUnit.SECONDS.toMillis(5));
        map.put("a", "111");
        map.put("b", "222");

        int index = 0;
        while (true) {
            index++;

            if (index > 10) {
                break;
            }

            System.out.println(map.get("a"));

            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(800, 1200));
        }
    }
}
