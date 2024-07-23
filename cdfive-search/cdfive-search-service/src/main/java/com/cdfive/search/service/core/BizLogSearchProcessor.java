package com.cdfive.search.service.core;

import com.cdfive.common.util.PageUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.query.build.QueryBuilderContext;
import com.cdfive.es.query.build.QueryFactory;
import com.cdfive.es.query.build.QueryParameter;
import com.cdfive.es.query.build.impl.BaseQueryBuilder;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.search.eo.BizLogEo;
import com.cdfive.search.repository.BizLogEsRepository;
import com.cdfive.search.transformer.QueryBizLogPageTransformer;
import com.cdfive.search.vo.bizlog.QueryBizLogPageReqVo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Component
public class BizLogSearchProcessor {

    @Autowired
    private BizLogEsRepository bizLogEsRepository;

    @Autowired
    private QueryBizLogPageTransformer queryBizLogPageTransformer;

    public PageRespVo<QueryBizLogPageRespVo> queryBizLogPage(QueryBizLogPageReqVo reqVo) {
        SearchQuery searchQuery = new QueryFactory<BizLogQueryParameter>().of("queryBizLogPage", (queryBuilder) -> {
            queryBuilder.withBuilder(new BizLogIdQueryBuilder())
                    .withBuilder(new BizLogInfoQueryBuilder())
                    .withBuilder(new BizLogKeyIdQueryBuilder())
                    .withHandler(new BizLogQueryHandler());
        }).buildSearchQuery(QueryParameter.from(reqVo, BizLogQueryParameter.class));

        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        return PageUtil.buildPage(page, queryBizLogPageTransformer);
    }
}
