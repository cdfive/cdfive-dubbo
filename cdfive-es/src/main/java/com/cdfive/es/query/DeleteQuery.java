package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;

import java.io.Serializable;

/**
 * @author cdfive
 */
public class DeleteQuery implements Serializable {

    private QueryBuilder query;

    private Integer batchSize;

    public DeleteQuery() {

    }

    public DeleteQuery(QueryBuilder query) {
        this.query = query;
    }

    public DeleteQuery(QueryBuilder query, Integer batchSize) {
        this.query = query;
        this.batchSize = batchSize;
    }

    public DeleteQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public DeleteQuery withBatchSize(Integer batchSize) {
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
