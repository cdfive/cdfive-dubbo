package com.cdfive.learn.expiringmap;

import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cdfive
 */
public class ExpiringMapTest {

    public static void main(String[] args) throws Exception {
//        demo1();

        demo2();
    }

    // Exception in thread "main" java.util.NoSuchElementException: a
    //	at net.jodah.expiringmap.internal.Assert.element(Assert.java:30)
    //	at net.jodah.expiringmap.ExpiringMap.getExpectedExpiration(ExpiringMap.java:836)
    //	at com.cdfive.learn.expiringmap.ExpiringMapTest.main(ExpiringMapTest.java:22)
    public static void demo1() throws Exception {
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

    public static void demo2() throws Exception {
        String key = "cdfive";
        int maxVisitValue = 12;

        ExpiringMap<String, AtomicInteger> map = ExpiringMap.builder()
                .maxSize(1)
                .expiration(5, TimeUnit.SECONDS)
                .build();
        map.put(key, new AtomicInteger(0));

        while ((true)) {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(200));
            AtomicInteger value = map.get(key);

            System.out.println(String.format("value=%s,expireMs=%s", map.get(key).get(), map.getExpectedExpiration(key)));

            int visitValue = value.incrementAndGet();
            if (visitValue > maxVisitValue) {
                System.out.println("max visit value reached,visitValue=" + visitValue + ",maxVisitValue=" + maxVisitValue
                        + ",expireMs=%s" + map.getExpectedExpiration(key));
                break;
            }
        }
    }
}
