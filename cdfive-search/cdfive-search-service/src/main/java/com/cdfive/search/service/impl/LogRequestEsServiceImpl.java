package com.cdfive.search.service.impl;

import com.cdfive.common.util.CommonUtil;
import com.cdfive.common.util.PageUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.framework.message.vo.LogRequestMessageVo;
import com.cdfive.search.eo.LogRequestEo;
import com.cdfive.search.repository.LogRequestEsRepository;
import com.cdfive.search.service.LogRequestEsService;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

/**
 * @author cdfive
 */
@Slf4j
@Service("logRequestEsService")
public class LogRequestEsServiceImpl implements LogRequestEsService {

    @Autowired
    private LogRequestEsRepository logRequestEsRepository;

    @Override
    public void saveLogRequest(LogRequestMessageVo messageVo) {
        LogRequestEo eo = new LogRequestEo();
        eo.setId(CommonUtil.getTraceId());

        eo.setTraceId(messageVo.getTraceId());

        eo.setAppName(messageVo.getAppName());
        eo.setAppIp(messageVo.getAppIp());
        eo.setAppPort(messageVo.getAppPort());

        eo.setRequestUri(messageVo.getRequestUri());
        eo.setRemoteAddr(messageVo.getRemoteAddr());

        eo.setCostMs(messageVo.getCostMs());
        eo.setRequestBody(messageVo.getRequestBody());

        eo.setExExist(messageVo.getExExist());
        eo.setExClassName(messageVo.getExClassName());
        eo.setExStackTrace(messageVo.getExStackTrace());

        eo.setStartTime(messageVo.getStartTime());
        eo.setCreateTime(messageVo.getCreateTime());
        eo.setUpdateTime(new Date());
        logRequestEsRepository.save(eo);
    }

    @Override
    public PageRespVo<QueryLogRequestPageRespVo> queryLogRequestPage(QueryLogRequestPageReqVo reqVo) {
        BoolQueryBuilder rootQueryBuilder = QueryBuilders.boolQuery();

        String id = reqVo.getId();
        if (StringUtils.isNotBlank(id)) {
            rootQueryBuilder.filter(QueryBuilders.termQuery("id", id));
        }

        String traceId = reqVo.getTraceId();
        if (StringUtils.isNotBlank(traceId)) {
            rootQueryBuilder.filter(QueryBuilders.termQuery("traceId", traceId));
        }

        SearchQuery searchQuery = new SearchQuery(rootQueryBuilder, PageRequest.of(reqVo.getPageNum() - 1, reqVo.getPageSize()));
        searchQuery.withSort(SortBuilders.fieldSort("startTime").order(SortOrder.DESC));
        Page<EsEntityVo<LogRequestEo>> page = logRequestEsRepository.search(searchQuery);

        return PageUtil.buildPage(page, new Function<EsEntityVo<LogRequestEo>, QueryLogRequestPageRespVo>() {
            @Override
            public QueryLogRequestPageRespVo apply(EsEntityVo<LogRequestEo> esEntityVo) {
                LogRequestEo eo = esEntityVo.getEntity();

                QueryLogRequestPageRespVo respVo = new QueryLogRequestPageRespVo();
                respVo.setId(eo.getId());
                respVo.setTraceId(eo.getTraceId());
                respVo.setAppName(eo.getAppName());
                respVo.setAppIp(eo.getAppIp());
                respVo.setAppPort(eo.getAppPort());
                respVo.setRequestUri(eo.getRequestUri());
                respVo.setRemoteAddr(eo.getRemoteAddr());
                respVo.setCostMs(eo.getCostMs());
                respVo.setRequestBody(eo.getRequestBody());
                respVo.setExExist(eo.getExExist());
                respVo.setExClassName(eo.getExClassName());
//                respVo.setExStackTrace(eo.getExStackTrace());
                respVo.setStartTime(eo.getStartTime());
                respVo.setCreateTime(eo.getCreateTime());
                respVo.setUpdateTime(eo.getUpdateTime());
                return respVo;
            }
        });
    }
}
