package com.cdfive.search.service.core;

import com.cdfive.es.query.build.QueryBuilderContext;
import com.cdfive.es.query.build.impl.BaseQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author cdfive
 */
public class BizLogIdQueryBuilder extends BaseQueryBuilder<BizLogQueryParameter> {

    @Override
    protected void doBuild(QueryBuilderContext context, BizLogQueryParameter param) {
        BoolQueryBuilder rootQueryBuilder = context.getRootQueryBuilder();

        Integer id = param.getId();
        if (id != null) {
            rootQueryBuilder.filter(QueryBuilders.termQuery("id", id));
        }
    }
}
