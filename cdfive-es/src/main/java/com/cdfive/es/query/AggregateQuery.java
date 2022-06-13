package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 * @date 2022-01-05
 */
@SuppressWarnings("rawtypes")
public class AggregateQuery implements Serializable {

    private QueryBuilder query;

    private List<SortBuilder> sorts;

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

    public AggregateQuery(QueryBuilder query, List<SortBuilder> sorts, List<AggregationBuilder> aggregations) {
        this.query = query;
        this.sorts = sorts;
        this.aggregations = aggregations;
    }

    public AggregateQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public AggregateQuery withSort(SortBuilder sort) {
        if (sorts == null) {
            sorts = new ArrayList<>();
        }

        this.sorts.add(sort);
        return this;
    }

    public AggregateQuery withAggregation(AggregationBuilder aggregation) {
        if (aggregations == null) {
            aggregations = new ArrayList<>();
        }
        this.aggregations.add(aggregation);
        return this;
    }

    public SearchSourceBuilder toSearchSourceBuilder() {
        QueryBuilder queryBuilder = this.getQuery();


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        List<SortBuilder> sorts = this.getSorts();
        if (!CollectionUtils.isEmpty(sorts)) {
            for (SortBuilder sort : sorts) {
                searchSourceBuilder.sort(sort);
            }
        }

        List<AggregationBuilder> aggregations = this.getAggregations();
        if (!CollectionUtils.isEmpty(aggregations)) {
            for (AggregationBuilder aggregation : aggregations) {
                searchSourceBuilder.aggregation(aggregation);
            }
        }

        searchSourceBuilder.size(0);
        return searchSourceBuilder;
    }

    public QueryBuilder getQuery() {
        return query;
    }

    public void setQuery(QueryBuilder query) {
        this.query = query;
    }

    public List<SortBuilder> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortBuilder> sorts) {
        this.sorts = sorts;
    }

    public List<AggregationBuilder> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<AggregationBuilder> aggregations) {
        this.aggregations = aggregations;
    }
}
