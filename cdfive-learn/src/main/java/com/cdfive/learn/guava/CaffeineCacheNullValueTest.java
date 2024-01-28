package com.cdfive.learn.guava;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine can cache value of null
 *
 * @author cdfive
 */
public class CaffeineCacheNullValueTest {

    public static void main(String[] args) {
        LoadingCache<Integer, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return null;
                    }
                });

        // null
        String value = cache.get(1);
    }
}
