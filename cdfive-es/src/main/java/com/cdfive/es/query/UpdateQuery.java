package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdfive
 */
public class UpdateQuery implements Serializable {

    private QueryBuilder query;

    private Integer batchSize;

    private String script;

    private Map<String, Object> params;

    public UpdateQuery() {

    }

    public UpdateQuery(QueryBuilder query) {
        this.query = query;
    }

    public UpdateQuery(QueryBuilder query, Integer batchSize) {
        this.query = query;
        this.batchSize = batchSize;
    }

    public UpdateQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public UpdateQuery withBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public UpdateQuery withScript(String script) {
        this.script = script;
        return this;
    }

    private UpdateQuery withParam(String paramName, Object paramValue) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }

        this.params.put(paramName, paramValue);
        return this;
    }

    public QueryBuilder getQuery() {
        return query;
    }

    public void setQuery(QueryBuilder query) {
        this.query = query;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
