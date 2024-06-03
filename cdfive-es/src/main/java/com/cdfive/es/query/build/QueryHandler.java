package com.cdfive.es.query.build;


import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.SearchQuery;

/**
 * @author cdfive
 */
public interface QueryHandler {

    void handle(SearchQuery searchQuery, QueryParameter param);

    void handle(AggregateQuery aggregateQuery, QueryParameter param);
}
