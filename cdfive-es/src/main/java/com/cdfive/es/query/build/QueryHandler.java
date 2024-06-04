package com.cdfive.es.query.build;


import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.SearchQuery;

/**
 * @author cdfive
 */
public interface QueryHandler<Param> {

    void handle(SearchQuery searchQuery, Param param);

    void handle(AggregateQuery aggregateQuery, Param param);
}
