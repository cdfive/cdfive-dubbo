package com.cdfive.es.query.build.impl;


import com.cdfive.es.query.build.QueryBuilderContext;
import com.cdfive.es.query.build.QueryParameter;

/**
 * @author cdfive
 */
public class StartQueryBuilder<Param extends QueryParameter> extends BaseQueryBuilder<Param> {

    public StartQueryBuilder() {
        super();
    }

    @Override
    protected void doBuild(QueryBuilderContext context, QueryParameter param) {
        // do nothing
    }
}
