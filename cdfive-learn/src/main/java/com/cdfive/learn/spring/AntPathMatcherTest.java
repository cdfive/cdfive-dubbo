package com.cdfive.learn.spring;

import org.springframework.util.AntPathMatcher;

/**
 * @author cdfive
 */
public class AntPathMatcherTest {

    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        String pattern;
        String path;

        pattern = "/order/*";
        path = "/order/list";
        System.out.println(matcher.match(pattern, path)); // true
        path = "/order/other";
        System.out.println(matcher.match(pattern, path)); // true
        path = "/order/detail/1";
        System.out.println(matcher.match(pattern, path)); // false
        pattern = "/order/**";
        path = "/order/detail/1";
        System.out.println(matcher.match(pattern, path)); // true
        path = "/order/list";
        System.out.println(matcher.match(pattern, path)); // true
        path = "/order/other";
        System.out.println(matcher.match(pattern, path)); // true
    }
}
