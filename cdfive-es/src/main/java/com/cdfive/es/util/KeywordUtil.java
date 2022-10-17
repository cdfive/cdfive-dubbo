package com.cdfive.es.util;

import com.cdfive.es.vo.EsKeywordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class KeywordUtil {

    private static final Logger log = LoggerFactory.getLogger(KeywordUtil.class);

    // 阿拉伯数字
    private static final List<Character> DIGITS = Collections.unmodifiableList(new ArrayList<Character>() {
        {
            add('0');
            add('1');
            add('2');
            add('3');
            add('4');
            add('5');
            add('6');
            add('7');
            add('8');
            add('9');
        }
    });

    // 中文数字
    private static final List<Character> CHINESE = Collections.unmodifiableList(new ArrayList<Character>() {
        {
            add('零');
            add('一');
            add('二');
            add('三');
            add('四');
            add('五');
            add('六');
            add('七');
            add('八');
            add('九');
            add('十');
        }
    });

    // 阿拉伯数字+中文数字
    private static final List<Character> DIGITS_CHINESES = Collections.unmodifiableList(new ArrayList<Character>() {
        private static final long serialVersionUID = -810374661046204560L;

        {
            addAll(DIGITS);
            addAll(CHINESE);
        }
    });

    // 中文数字:十
    private static final Character NUMBERL_TEN_CHARACTER = '十';

    // 中文数字和阿拉伯数字映射
    private static final Map<Character, Character> CHINESE_DIGIT_MAPPING = Collections.unmodifiableMap(new HashMap<Character, Character>() {
        private static final long serialVersionUID = 421412483595983062L;

        {
            put('零', '0');
            put('一', '1');
            put('二', '2');
            put('三', '3');
            put('四', '4');
            put('五', '5');
            put('六', '6');
            put('七', '7');
            put('八', '8');
            put('九', '9');
        }
    });

    // 阿拉伯数字和中文数字映射
    private static final Map<Character, Character> DIGIT_CHINESE_MAPPING = CHINESE_DIGIT_MAPPING
            .entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    /**
     * 处理关键字分词
     */
    public static EsKeywordVo processKeywordTokens(String keyword, List<String> tokens) {
        EsKeywordVo esKeywordVo = EsKeywordVo.of(keyword, tokens);

        if (!hasNumeral(keyword)) {
            return esKeywordVo;
        }

        if (ObjectUtils.isEmpty(tokens)) {
            return esKeywordVo;
        }

        List<String> moreTokens = null;
        for (String token : tokens) {
            String mappingToken = null;
            try {
                mappingToken = mappingNumeral(token);
            } catch (Exception e) {
                log.error("mappingNumeral error,token={}", token, e);
                continue;
            }

            if (!token.equals(mappingToken)) {
                if (moreTokens == null) {
                    moreTokens = new ArrayList<>();
                }
                moreTokens.add(mappingToken);
            }
        }

        esKeywordVo.setMoreMappingTokens(moreTokens);
        return esKeywordVo;
    }

    /**
     * 映射阿拉伯数字和中文数字
     */
    public static String mappingNumeral(String keyword) {
        char[] chars = keyword.toCharArray();

        if (isAllDigit(keyword)) {
            if (chars.length == 2 && chars[0] == '1') {
                chars[0] = NUMBERL_TEN_CHARACTER;
            }

            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                chars[i] = DIGIT_CHINESE_MAPPING.getOrDefault(c, c);
            }
            return String.valueOf(chars);
        }

        if (chars.length == 2 && NUMBERL_TEN_CHARACTER.equals(Character.valueOf(chars[0])) && !NUMBERL_TEN_CHARACTER.equals(Character.valueOf(chars[1]))) {
            chars[0] = '1';
        }

        if (chars.length == 3 && NUMBERL_TEN_CHARACTER.equals(Character.valueOf(chars[1]))
                && DIGITS.contains(Character.valueOf(chars[0]))
                && DIGITS.contains(Character.valueOf(chars[2]))) {
            chars = new char[2];
            chars[0] = keyword.charAt(0);
            chars[1] = keyword.charAt(2);
        }

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (DIGITS.contains(Character.valueOf(c))) {
                continue;
            }

            chars[i] = CHINESE_DIGIT_MAPPING.getOrDefault(c, c);
        }
        return String.valueOf(chars);
    }

    /**
     * 判断关键字是否都是数字
     */
    public static boolean isAllDigit(String keyword) {
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            if (!DIGITS.contains(Character.valueOf(c))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断关键字中是否有阿拉伯数字和中文数字
     */
    public static boolean hasNumeral(String keyword) {
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            if (DIGITS_CHINESES.contains(Character.valueOf(c))) {
                return true;
            }
        }

        return false;
    }
}
