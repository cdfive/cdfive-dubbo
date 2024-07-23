package com.cdfive.search.service.core;

import com.cdfive.es.query.build.QueryParameter;

/**
 * @author cdfive
 */
public class BizLogQueryParameter implements QueryParameter {

    private Integer id;

    private String info;

    private Integer keyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }
}
