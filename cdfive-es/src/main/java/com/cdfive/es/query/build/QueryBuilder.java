package com.cdfive.es.query.build;


import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.SearchQuery;

/**
 * @author cdfive
 */
public interface QueryBuilder {

    void build(QueryBuilderContext context, QueryParameter param);

    SearchQuery buildSearchQuery(QueryParameter param);

    AggregateQuery buildAggregateQuery(QueryParameter param);
}
