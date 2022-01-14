package com.cdfive.es.constant;

/**
 * @author cdfive
 */
public class EsConstant {

    public static final Long MAX_RESULT = 10000L;

    // ik分词器相关
    public static final String ANALYZER_IK_SMART = "ik_smart";
    public static final String ANALYZER_IK_MAX_WORD = "ik_max_word";

    // should查询默认配置
    public static final String MINIMUM_SHOULD_MATCH_DEFAULT = "2<70%";

    // 脚本类型相关
    public static String LANG_PAINLESS = "painless";

    // 冲突处理相关
    public static final String CONFLICTS_PROCEED = "proceed";
    public static final String CONFLICTS_ABORT = "abort";
}
