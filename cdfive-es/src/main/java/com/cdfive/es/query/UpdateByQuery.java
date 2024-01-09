package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdfive
 * @date 2022-01-05
 */
public class UpdateByQuery implements Serializable {

    private static final long serialVersionUID = 6555057248028930805L;

    private QueryBuilder query;

    private String script;

    private String scriptId;

    private Map<String, Object> params;

    private Integer batchSize;

    private Boolean conflictAbort;

    private Boolean refresh;

    private Boolean async;

    private Runnable callback;

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

    public UpdateByQuery withConflictAbort(Boolean conflictAbort) {
        this.conflictAbort = conflictAbort;
        return this;
    }

    public UpdateByQuery withScript(String script) {
        this.script = script;
        return this;
    }

    public UpdateByQuery withScriptId(String scriptId) {
        this.scriptId = scriptId;
        return this;
    }

    public UpdateByQuery withParam(String paramName, Object paramValue) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }

        this.params.put(paramName, paramValue);
        return this;
    }

    public UpdateByQuery withRefresh(Boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    public UpdateByQuery withAsync(Boolean async) {
        this.async = async;
        return this;
    }

    public UpdateByQuery withCallback(Runnable callback) {
        this.callback = callback;
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

    public Boolean getConflictAbort() {
        return conflictAbort;
    }

    public void setConflictAbort(Boolean conflictAbort) {
        this.conflictAbort = conflictAbort;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Boolean getRefresh() {
        return refresh;
    }

    public void setRefresh(Boolean refresh) {
        this.refresh = refresh;
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public Runnable getCallback() {
        return callback;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
}
