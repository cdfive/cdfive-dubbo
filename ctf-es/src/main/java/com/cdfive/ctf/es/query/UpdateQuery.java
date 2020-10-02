package com.cdfive.ctf.es.query;

import org.elasticsearch.index.query.QueryBuilder;

import java.io.Serializable;

/**
 * @author cdfive
 */
public class UpdateQuery implements Serializable {

    private QueryBuilder query;

    private Integer batchSize;

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
}
