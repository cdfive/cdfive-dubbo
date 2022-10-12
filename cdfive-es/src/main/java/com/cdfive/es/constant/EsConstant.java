package com.cdfive.es.constant;

/**
 * ES常用常量
 *
 * @author cdfive
 */
public class EsConstant {

    // 最大结果集数量
    public static final Long MAX_RESULT = 10000L;

    // ik分词器相关
    public static final String ANALYZER_IK_SMART = "ik_smart";
    public static final String ANALYZER_IK_MAX_WORD = "ik_max_word";

    // 拼音分词器相关
    public static final String ANALYZER_PINYIN = "pinyin";

    // should查询默认配置
    public static final String MINIMUM_SHOULD_MATCH_DEFAULT = "2<70%";

    // 冲突处理相关
    public static final String CONFLICTS_PROCEED = "proceed";
    public static final String CONFLICTS_ABORT = "abort";

    // 打分相关
    public String SCORE = "_score";

    // 空排序值数组
    public static final Object[] EMPTY_SORT_VALUES = new Object[0];
}
