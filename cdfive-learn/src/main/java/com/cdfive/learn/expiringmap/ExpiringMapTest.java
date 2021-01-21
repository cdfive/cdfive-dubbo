package com.cdfive.learn.expiringmap;

import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class ExpiringMapTest {

    public static void main(String[] args) throws Exception {
        ExpiringMap<String, Integer> map = ExpiringMap.builder()
                .maxSize(5)
                .expiration(10, TimeUnit.SECONDS)
                .build();

        map.put("a", 1);
        while (true) {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(200));
            System.out.println(String.format("value=%s,expireMs=%s", map.get("a"), map.getExpectedExpiration("a")));
        }
    }

    // Exception in thread "main" java.util.NoSuchElementException: a
    //	at net.jodah.expiringmap.internal.Assert.element(Assert.java:30)
    //	at net.jodah.expiringmap.ExpiringMap.getExpectedExpiration(ExpiringMap.java:836)
    //	at com.cdfive.learn.expiringmap.ExpiringMapTest.main(ExpiringMapTest.java:22)
}
