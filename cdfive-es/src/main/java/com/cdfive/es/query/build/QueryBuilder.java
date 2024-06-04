package com.cdfive.es.query.build;


import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.SearchQuery;

/**
 * @author cdfive
 */
public interface QueryBuilder<Param> {

    void build(QueryBuilderContext context, Param param);

    SearchQuery buildSearchQuery(Param param);

    AggregateQuery buildAggregateQuery(Param param);
}
