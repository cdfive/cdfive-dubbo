package com.cdfive.es.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 */
public class BatchUpdateRespVo<ID> implements Serializable {

    private List<ID> successIds;

    private List<ID> errorIds;

    private String traceId;

    public BatchUpdateRespVo() {
        this.successIds = new ArrayList<>();
        this.errorIds = new ArrayList<>();
    }

    public BatchUpdateRespVo(String traceId) {
        this();
        this.traceId = traceId;
    }

    public List<ID> getSuccessIds() {
        return successIds;
    }

    public void setSuccessIds(List<ID> successIds) {
        this.successIds = successIds;
    }

    public List<ID> getErrorIds() {
        return errorIds;
    }

    public void setErrorIds(List<ID> errorIds) {
        this.errorIds = errorIds;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
