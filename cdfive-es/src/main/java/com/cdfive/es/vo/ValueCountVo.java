package com.cdfive.es.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfive
 */
public class ValueCountVo implements Serializable {

    private String value;

    private Long count;

    private List<String> subValues;

    public ValueCountVo() {

    }

    public ValueCountVo(String value, Long count) {
        this.value = value;
        this.count = count;
    }

    public ValueCountVo(String value, Long count, List<String> subValues) {
        this.value = value;
        this.count = count;
        this.subValues = subValues;
    }

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
}
