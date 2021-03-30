package com.cdfive.es.vo;

import java.io.Serializable;

/**
 * @author cdfive
 */
public class ValueCountVo implements Serializable {

    private String value;

    private Long count;

    public ValueCountVo() {

    }

    public ValueCountVo(String value, Long count) {
        this.value = value;
        this.count = count;
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
