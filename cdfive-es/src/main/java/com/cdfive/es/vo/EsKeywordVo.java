package com.cdfive.es.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
public class EsKeywordVo implements Serializable {

    private String keyword;

    private List<String> analyzeTokens;

    private List<String> moreMappingTokens;

    public EsKeywordVo() {

    }

    public EsKeywordVo(String keyword, List<String> analyzeTokens, List<String> moreMappingTokens) {
        this.keyword = keyword;
        this.analyzeTokens = analyzeTokens;
        this.moreMappingTokens = moreMappingTokens;
    }

    public EsKeywordVo(String keyword) {
        this.keyword = keyword;
    }

    public EsKeywordVo(String keyword, List<String> analyzeTokens) {
        this.keyword = keyword;
        this.analyzeTokens = analyzeTokens;
    }

    public static EsKeywordVo of(String keyword) {
        return new EsKeywordVo(keyword);
    }

    public static EsKeywordVo of(String keyword, List<String> analyzeTokens) {
        return new EsKeywordVo(keyword, analyzeTokens);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getAnalyzeTokens() {
        return analyzeTokens;
    }

    public void setAnalyzeTokens(List<String> analyzeTokens) {
        this.analyzeTokens = analyzeTokens;
    }

    public List<String> getMoreMappingTokens() {
        return moreMappingTokens;
    }

    public void setMoreMappingTokens(List<String> moreMappingTokens) {
        this.moreMappingTokens = moreMappingTokens;
    }
}
