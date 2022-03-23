package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;

import java.io.Serializable;

/**
 * @author cdfive
 * @date 2022-01-05
 */
public class DeleteByQuery implements Serializable {

    private QueryBuilder query;

    private Integer batchSize;

    private Boolean conflictAbort;

    private Boolean refresh;

    public static DeleteByQuery of() {
        return new DeleteByQuery();
    }

    public static DeleteByQuery of(QueryBuilder query) {
        return new DeleteByQuery(query);
    }

    public static DeleteByQuery of(QueryBuilder query, Integer batchSize) {
        return new DeleteByQuery(query, batchSize);
    }

    public DeleteByQuery() {

    }

    public DeleteByQuery(QueryBuilder query) {
        this.query = query;
    }

    public DeleteByQuery(QueryBuilder query, Integer batchSize) {
        this.query = query;
        this.batchSize = batchSize;
    }

    public DeleteByQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public DeleteByQuery withBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public DeleteByQuery withConflictAbort(Boolean conflictAbort) {
        this.conflictAbort = conflictAbort;
        return this;
    }

    public DeleteByQuery withRefresh(Boolean refresh) {
        this.refresh = refresh;
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

    public Boolean getRefresh() {
        return refresh;
    }

    public void setRefresh(Boolean refresh) {
        this.refresh = refresh;
    }
}
