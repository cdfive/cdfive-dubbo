package com.cdfive.learn.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * expireAfterAccess: 当缓存项在指定的时间段内没有被读或写就会被回收
 *
 * expireAfterWrite: 当缓存项在指定的时间段内没有更新就会被回收,限制1个线程加锁加载,其它请求等待
 *
 * refreshAfterWrite: 当缓存项在指定的时间段内没有更新就会被回收,限制1个线程加载,其它请求返回旧值
 *
 * 最佳实践:expireAfterWrite + expireAfterWrite结合使用?
 * 如:.refreshAfterWrite(5, TimeUnit.SECONDS) // 缓存5秒
 *   .expireAfterWrite(30, TimeUnit.SECONDS) // 设置30秒,防止长时间没有查询请求,返回旧值
 *
 * @author cdfive
 */
public class CacheTest1 {

    public static void main(String[] args) throws Exception{
        LoadingCache<Integer, String> userMobileCache = CacheBuilder.newBuilder()
                .initialCapacity(5)
                .maximumSize(500)
                .expireAfterAccess(5, TimeUnit.SECONDS)
//                .expireAfterWrite(5, TimeUnit.SECONDS)
//                .refreshAfterWrite(5, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer memberId) throws Exception {
                        if (memberId == 1) {
                            System.out.println("load 1");
                            return "1";
                        }
                        System.out.println("load 2");
                        return "2";
                    }
                });

        while (true) {
            String s = userMobileCache.get(1);
            System.out.println(formatNow() + "=>" + s);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static String formatNow() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
    }
}
