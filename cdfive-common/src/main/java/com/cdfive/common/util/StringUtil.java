package com.cdfive.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cdfive
 */
public class StringUtil {

    private static final String REGEX_SPECIAL_STR = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    private static Pattern PATTERN_REGEX_SPECIAL_STR = Pattern.compile(REGEX_SPECIAL_STR);

    public static boolean isBlank(final CharSequence cs) {
        if (cs == null) {
            return true;
        }

        int length = cs.length();
        if (length == 0) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String removeSpecialStr(String str) {
        Matcher m = PATTERN_REGEX_SPECIAL_STR.matcher(str);
        return m.replaceAll("").trim();
    }

    private StringUtil() {

    }
}
