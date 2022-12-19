package com.cdfive.es.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 */
public class BatchUpdateRespVo<ID> implements Serializable {

    private List<ID> successItems;

    private List<ID> errorItems;

    private String traceId;

    public BatchUpdateRespVo() {
        this.successItems = new ArrayList<>();
        this.errorItems = new ArrayList<>();
    }

    public BatchUpdateRespVo(String traceId) {
        this();
        this.traceId = traceId;
    }

    public List<ID> getSuccessItems() {
        return successItems;
    }

    public void setSuccessItems(List<ID> successItems) {
        this.successItems = successItems;
    }

    public List<ID> getErrorItems() {
        return errorItems;
    }

    public void setErrorItems(List<ID> errorItems) {
        this.errorItems = errorItems;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
