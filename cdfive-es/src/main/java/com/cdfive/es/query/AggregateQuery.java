package com.cdfive.es.query;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 */
public class AggregateQuery implements Serializable {

    private QueryBuilder query;

    private List<AggregationBuilder> aggregations = new ArrayList<>();

    public AggregateQuery() {

    }

    public AggregateQuery(QueryBuilder query) {
        this.query = query;
    }

    public AggregateQuery(QueryBuilder query, List<AggregationBuilder> aggregations) {
        this.query = query;
        this.aggregations = aggregations;
    }

    public SearchSourceBuilder toSearchSourcebuilder() {
        QueryBuilder queryBuilder = this.getQuery();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        List<AggregationBuilder> aggregations = this.getAggregations();
        if (!CollectionUtils.isEmpty(aggregations)) {
            for (AggregationBuilder aggregation : aggregations) {
                searchSourceBuilder.aggregation(aggregation);
            }
        }

        searchSourceBuilder.size(0);
        return searchSourceBuilder;
    }

    public AggregateQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public AggregateQuery withAggregation(AggregationBuilder aggregation) {
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
