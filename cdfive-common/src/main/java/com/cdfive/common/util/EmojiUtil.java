package com.cdfive.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cdfive
 */
public class EmojiUtil {

    public static final String HIDDEN_STRING = "***";

    private static final Pattern PATTERN_EMOJI = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    public static boolean containsEmoji(String str) {
        if (str == null) {
            return false;
        }
        Matcher m = PATTERN_EMOJI.matcher(str);
        return m.find();
    }

    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    public static String hideName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        if (name.length() < 2) {
            return name + HIDDEN_STRING;
        }

        boolean firstEmoji = isEmojiCharacter(name.charAt(0));
        boolean lastEmoji = isEmojiCharacter(name.charAt(name.length() - 1));

        if (firstEmoji && lastEmoji && name.length() == 2) {
            return name + HIDDEN_STRING;
        }

        return name.substring(0, firstEmoji ? 2 : 1) + HIDDEN_STRING + name.substring(lastEmoji ? name.length() - 2 : name.length() - 1);
    }
}
