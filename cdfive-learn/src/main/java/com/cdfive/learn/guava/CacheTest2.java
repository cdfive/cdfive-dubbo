package com.cdfive.learn.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
public class CacheTest2 {

    public static void main(String[] args) throws Exception {
        LoadingCache<String, List<Integer>> cache = CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<String, List<Integer>>() {
                    @Override
                    public List<Integer> load(String s) throws Exception {
                        return Lists.newArrayList(1, 2, 3);
                    }
                });

        // test case1
        System.out.println(StringUtils.center("case1", 50, "-"));
        List<Integer> list1 = cache.get("all");
        System.out.println(list1);
        list1.removeIf(o -> o.equals(1));
        System.out.println(list1);
        List<Integer> list2 = cache.get("all");
        System.out.println(list2);

        list1 = null;
        cache.invalidateAll();

        // test case2
        System.out.println(StringUtils.center("case2", 50, "-"));
        list1 = cache.get("all");
        System.out.println(list1);
        list1 = Lists.newArrayList(1);
        System.out.println(list1);
        list2 = cache.get("all");
        System.out.println(list2);
    }
}
