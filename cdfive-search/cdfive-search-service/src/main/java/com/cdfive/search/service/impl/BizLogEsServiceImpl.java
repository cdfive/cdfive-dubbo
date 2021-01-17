package com.cdfive.search.service.impl;

import com.cdfive.common.util.PageUtil;
import com.cdfive.common.util.StringUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.search.eo.BizLogEo;
import com.cdfive.search.repository.BizLogEsRepository;
import com.cdfive.search.service.BizLogEsService;
import com.cdfive.search.transformer.QueryBizLogPageTransformer;
import com.cdfive.search.vo.bizlog.QueryBizLogPageReqVo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;
import com.cdfive.search.vo.bizlog.SaveBizLogReqVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@Service("bizLogEsService")
public class BizLogEsServiceImpl implements BizLogEsService {

    @Autowired
    private BizLogEsRepository bizLogEsRepository;

    @Override
    public void saveBizLogs(List<SaveBizLogReqVo> reqVos) {
        if (ObjectUtils.isEmpty(reqVos)) {
            return;
        }

        List<BizLogEo> eos = reqVos.stream().map(o -> {
            BizLogEo eo = new BizLogEo();
            BeanUtils.copyProperties(o, eo);
            return eo;
        }).collect(Collectors.toList());
        bizLogEsRepository.save(eos);
    }

    @Override
    public PageRespVo<QueryBizLogPageRespVo> queryBizLogPage(QueryBizLogPageReqVo reqVo) {
        BoolQueryBuilder rootQueryBuilder = QueryBuilders.boolQuery();

        String info = reqVo.getInfo();
        if (StringUtil.isNotBlank(info)) {
            rootQueryBuilder.filter(QueryBuilders.matchQuery("info", info));
        }

        SearchQuery searchQuery = new SearchQuery(rootQueryBuilder, PageRequest.of(reqVo.getPageNum() - 1, reqVo.getPageSize()));
        Page<BizLogEo> page = bizLogEsRepository.search(searchQuery);
        return PageUtil.buildPage(page, QueryBizLogPageTransformer.INSTANCE);
    }
}
