package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;

import java.io.Serializable;

/**
 * @author cdfive
 */
public class CountQuery implements Serializable {

    private static final long serialVersionUID = 2890276392886164954L;

    private QueryBuilder query;

    public CountQuery of() {
        return new CountQuery();
    }

    public CountQuery of(QueryBuilder query) {
        return new CountQuery(query);
    }

    public CountQuery() {

    }

    public CountQuery(QueryBuilder query) {
        this.query = query;
    }

    public QueryBuilder getQuery() {
        return query;
    }

    public void setQuery(QueryBuilder query) {
        this.query = query;
    }
}
