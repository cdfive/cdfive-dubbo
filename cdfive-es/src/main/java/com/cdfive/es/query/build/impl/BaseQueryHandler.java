package com.cdfive.es.query.build.impl;

import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.query.build.QueryHandler;
import com.cdfive.es.query.build.QueryParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cdfive
 */
public abstract class BaseQueryHandler implements QueryHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(SearchQuery searchQuery, QueryParameter param) {

    }

    @Override
    public void handle(AggregateQuery aggregateQuery, QueryParameter param) {

    }
}
