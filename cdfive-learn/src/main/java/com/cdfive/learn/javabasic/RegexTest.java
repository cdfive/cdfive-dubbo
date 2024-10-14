package com.cdfive.learn.javabasic;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author cdfive
 */
public class RegexTest {

    /**
     * @see org.springframework.boot.actuate.endpoint.EndpointId#EndpointId
     */
    private static final Pattern VALID_PATTERN = Pattern.compile("[a-zA-Z0-9.-]+");

    private static final Pattern TEST_PATTERN = Pattern.compile("^[0-9a-zA-Z]{1,20}$");

    public static void main(String[] args) {
        // false
        System.out.println(VALID_PATTERN.matcher("").matches());
        // false
        System.out.println(VALID_PATTERN.matcher("*").matches());
        // false
        System.out.println(VALID_PATTERN.matcher("\\*").matches());
        // true
        System.out.println(VALID_PATTERN.matcher(".").matches());
        // false
        System.out.println(VALID_PATTERN.matcher("\\.").matches());
        // true
        System.out.println(VALID_PATTERN.matcher("health").matches());
        // true
        System.out.println(VALID_PATTERN.matcher("Health").matches());

        System.out.println(StringUtils.center("分隔线", 50, "-"));

        // false
        System.out.println(TEST_PATTERN.matcher("").matches());
        // false
        System.out.println(TEST_PATTERN.matcher("!").matches());
        // true
        System.out.println(TEST_PATTERN.matcher("123").matches());
        // true
        System.out.println(TEST_PATTERN.matcher("123ab").matches());
        // true
        System.out.println(TEST_PATTERN.matcher("123abZ").matches());
        // true
        System.out.println(TEST_PATTERN.matcher("01234567890123456789").matches());
        // false
        System.out.println(TEST_PATTERN.matcher("01234567890123456789a").matches());
    }
}
