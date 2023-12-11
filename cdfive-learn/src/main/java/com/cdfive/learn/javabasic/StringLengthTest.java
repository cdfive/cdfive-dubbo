package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

/**
 * @author cdfive
 */
public class StringLengthTest {

    public static void main(String[] args) throws Exception {
        // UTF-8
        System.out.println(Charset.defaultCharset().name());

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        String s1 = "a";
        // 1
        System.out.println(s1.length());
        // 1
        System.out.println(s1.getBytes("UTF-8").length);
        // 1
        System.out.println(s1.getBytes("GBK").length);
        // 1
        System.out.println(s1.getBytes("GB2312").length);
        // 1
        System.out.println(s1.getBytes("ISO8859-1").length);

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        String s2 = "相思";
        // 2
        System.out.println(s2.length());
        // 6
        System.out.println(s2.getBytes().length);
        // 6
        System.out.println(s2.getBytes("UTF-8").length);
        // 4
        System.out.println(s2.getBytes("GBK").length);
        // 4
        System.out.println(s2.getBytes("GB2312").length);
        // 2
        System.out.println(s2.getBytes("ISO8859-1").length);
    }
}
