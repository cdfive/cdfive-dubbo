package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiejihan
 * @date 2022-01-05
 */
public class UpdateByQuery implements Serializable {

    private QueryBuilder query;

    private Integer batchSize;

    private String script;

    private Map<String, Object> params;

    public static UpdateByQuery of() {
        return new UpdateByQuery();
    }

    public static UpdateByQuery of(QueryBuilder query) {
        return new UpdateByQuery(query);
    }

    public static UpdateByQuery of(QueryBuilder query, Integer batchSize) {
        return new UpdateByQuery(query, batchSize);
    }

    public UpdateByQuery() {

    }

    public UpdateByQuery(QueryBuilder query) {
        this.query = query;
    }

    public UpdateByQuery(QueryBuilder query, Integer batchSize) {
        this.query = query;
        this.batchSize = batchSize;
    }

    public UpdateByQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public UpdateByQuery withBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public UpdateByQuery withScript(String script) {
        this.script = script;
        return this;
    }

    private UpdateByQuery withParam(String paramName, Object paramValue) {
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