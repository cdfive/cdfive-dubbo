package com.cdfive.es.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author cdfive
 */
public class EsValueCountVo implements Serializable {

    public EsValueCountVo() {

    }

    public EsValueCountVo(String value, Long count) {
        this.value = value;
        this.count = count;
    }

    public EsValueCountVo(String value, Long count, List<String> subValues) {
        this.value = value;
        this.count = count;
        this.subValues = subValues;
    }

    public EsValueCountVo(String value, Long count, List<String> subValues, Map<String, Double> numericMetrics) {
        this.value = value;
        this.count = count;
        this.subValues = subValues;
        this.numericMetrics = numericMetrics;
    }

    private String value;

    private Long count;

    private List<String> subValues;

    private Map<String, Double> numericMetrics;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<String> getSubValues() {
        return subValues;
    }

    public void setSubValues(List<String> subValues) {
        this.subValues = subValues;
    }

    public Map<String, Double> getNumericMetrics() {
        return numericMetrics;
    }

    public void setNumericMetrics(Map<String, Double> numericMetrics) {
        this.numericMetrics = numericMetrics;
    }
}
