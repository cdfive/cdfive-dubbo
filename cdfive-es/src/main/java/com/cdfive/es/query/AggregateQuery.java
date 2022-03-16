package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 * @date 2022-01-05
 */
public class AggregateQuery implements Serializable {

    private QueryBuilder query;

    private List<AggregationBuilder> aggregations;

    public static AggregateQuery of() {
        return new AggregateQuery();
    }

    public static AggregateQuery of(QueryBuilder query) {
        return new AggregateQuery(query);
    }

    public static AggregateQuery of(QueryBuilder query, List<AggregationBuilder> aggregations) {
        return new AggregateQuery(query, aggregations);
    }

    public AggregateQuery() {

    }

    public AggregateQuery(QueryBuilder query) {
        this.query = query;
    }

    public AggregateQuery(QueryBuilder query, List<AggregationBuilder> aggregations) {
        this.query = query;
        this.aggregations = aggregations;
    }

    public AggregateQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public AggregateQuery withAggregation(AggregationBuilder aggregation) {
        if (aggregations == null) {
            aggregations = new ArrayList<>();
        }
        this.aggregations.add(aggregation);
        return this;
    }

    public QueryBuilder getQuery() {
        return query;
    }

    public void setQuery(QueryBuilder query) {
        this.query = query;
    }

    public List<AggregationBuilder> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<AggregationBuilder> aggregations) {
        this.aggregations = aggregations;
    }
}
