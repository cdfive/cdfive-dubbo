package com.cdfive.search.service.core;

import com.cdfive.es.query.build.QueryBuilderContext;
import com.cdfive.es.query.build.impl.BaseQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author cdfive
 */
public class BizLogKeyIdQueryBuilder extends BaseQueryBuilder<BizLogQueryParameter> {

    @Override
    protected void doBuild(QueryBuilderContext context, BizLogQueryParameter param) {
        BoolQueryBuilder rootQueryBuilder = context.getRootQueryBuilder();

        Integer keyId = param.getKeyId();
        if (keyId != null) {
            rootQueryBuilder.filter(QueryBuilders.termQuery("keyId", keyId));
        }
    }
}
