package com.cdfive.search.service.core;

import com.cdfive.common.util.StringUtil;
import com.cdfive.es.query.build.QueryBuilderContext;
import com.cdfive.es.query.build.impl.BaseQueryBuilder;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author cdfive
 */
public class BizLogInfoQueryBuilder extends BaseQueryBuilder<BizLogQueryParameter> {

    @Override
    protected void doBuild(QueryBuilderContext context, BizLogQueryParameter param) {
        BoolQueryBuilder rootQueryBuilder = context.getRootQueryBuilder();

        String info = param.getInfo();
        if (StringUtil.isNotBlank(info)) {
            rootQueryBuilder.filter(QueryBuilders.matchQuery("info", QueryParser.escape(info)));
        }
    }
}
