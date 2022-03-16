package com.cdfive.learn.datastructure.map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author cdfive
 */
public class HashCodeTest {

    /**
     * @see HASH_INCREMENT in {@link java.lang.ThreadLocal}
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) {
        int len = 2 << 3; // 16

        System.out.println(StringUtils.center("test hash of Integer", 100, "-"));
        for (int i = 0; i < len; i++) {
            int hashCode = Integer.valueOf(i).hashCode();
            System.out.println(i + ",hashIndex=" + (hashCode & (len - 1)) + ",hashCode=" + hashCode);
        }

        System.out.println(StringUtils.center("test hash of Long", 100, "-"));
        for (int i = 0; i < len; i++) {
            int hashCode = Long.valueOf(i).hashCode();
            System.out.println(i + ",hashIndex=" + (hashCode & (len - 1)) + ",hashCode=" + hashCode);
        }

        System.out.println(StringUtils.center("test hash of String", 100, "-"));
        for (int i = 0; i < len; i++) {
            int hashCode = String.valueOf(i).hashCode();
            System.out.println(i + ",hashIndex=" + (hashCode & (len - 1)) + ",hashCode=" + hashCode);
        }

        System.out.println(StringUtils.center("test hash of using HASH_INCREMENT in ThreadLocal", 100, "-"));
        for (int i = 0; i < len; i++) {
            int hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            System.out.println(i + ",hashIndex=" + (hashCode & (len - 1)) + ",hashCode=" + hashCode);
        }
    }
}
