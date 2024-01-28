package com.cdfive.learn.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * Guava can not cache value of null
 * can use value of Optional instead
 *
 * @author cdfive
 */
public class GuavaCacheNullValueTest {

    public static void main(String[] args) throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return null;
                    }
                });
        String value = cache.get(1);

        /*
         Exception in thread "main" com.google.common.cache.CacheLoader$InvalidCacheLoadException: CacheLoader returned null for key 1.
	at com.google.common.cache.LocalCache$Segment.getAndRecordStats(LocalCache.java:2455)
	at com.google.common.cache.LocalCache$Segment.loadSync(LocalCache.java:2417)
	at com.google.common.cache.LocalCache$Segment.lockedGetOrLoad(LocalCache.java:2299)
	at com.google.common.cache.LocalCache$Segment.get(LocalCache.java:2212)
	at com.google.common.cache.LocalCache.get(LocalCache.java:4147)
	at com.google.common.cache.LocalCache.getOrLoad(LocalCache.java:4151)
	at com.google.common.cache.LocalCache$LocalLoadingCache.get(LocalCache.java:5140)
         */
        System.out.println(value);
    }
}
