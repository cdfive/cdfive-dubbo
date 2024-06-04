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
public abstract class BaseQueryHandler<Param extends QueryParameter> implements QueryHandler<Param> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(SearchQuery searchQuery, Param param) {

    }

    @Override
    public void handle(AggregateQuery aggregateQuery, Param param) {

    }
}
