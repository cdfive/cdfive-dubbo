package com.cdfive.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * @author cdfive
 */
public class GenerateUtil {

    public static String genRandomChars(int length) {
        char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        while (count < length) {
            int maxAscii = 'z';
            int randomAscii = random.nextInt(maxAscii);
            boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count] = (char) (randomAscii);
                ++count;
            }
        }
        return new String(randomChars);
    }

    public static String genRandomStrId() {
        return String.format("%s-%s-%s", HostNameUtil.getLastfieldOfHostName(), System.currentTimeMillis(), genRandomChars(4));
    }
}
