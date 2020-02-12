package com.cdfive.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cdfive
 */
public class StringUtil {

    private static final String REGEX_SPECIAL_STR = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    private static Pattern PATTERN_REGEX_SPECIAL_STR = Pattern.compile(REGEX_SPECIAL_STR);

    public static boolean isBlank(String s) {
        return StringUtils.isBlank(s);
    }

    public static String removeSpecialStr(String str) {
        Matcher m = PATTERN_REGEX_SPECIAL_STR.matcher(str);
        return m.replaceAll("").trim();
    }
}
