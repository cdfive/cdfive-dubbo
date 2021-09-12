package com.cdfive.common.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

/**
 * @author cdfive
 */
@Slf4j
public class PinyinUtil {

    private static final HanyuPinyinOutputFormat outputFormat;

    static {
        outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    }

    public static String toPinyin(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }

        StringBuilder result = new StringBuilder();
        char[] chrs = str.toCharArray();
        try {
            for (char chr : chrs) {
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(chr, outputFormat);
                if (pinyins != null) {
                    result.append(pinyins[0]);
                } else {
                    result.append(chr);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("toPinyin error,str={}", str, e);
            return str;
        }

        return result.toString();
    }

    public static boolean isPinyin(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }

        return str.equals(toPinyin(str));
    }
}
