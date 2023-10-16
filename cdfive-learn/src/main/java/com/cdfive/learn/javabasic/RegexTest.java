package com.cdfive.learn.javabasic;

import java.util.regex.Pattern;

/**
 * @author cdfive
 */
public class RegexTest {

    /**
     * @see org.springframework.boot.actuate.endpoint.EndpointId#EndpointId
     */
    private static Pattern VALID_PATTERN = Pattern.compile("[a-zA-Z0-9.-]+");

    public static void main(String[] args) {
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
    }
}
