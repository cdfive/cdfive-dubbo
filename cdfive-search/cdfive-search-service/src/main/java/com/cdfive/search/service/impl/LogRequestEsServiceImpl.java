package com.cdfive.search.service.impl;

import com.cdfive.common.util.CommonUtil;
import com.cdfive.common.util.PageUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.search.eo.LogRequestEo;
import com.cdfive.search.repository.LogRequestEsRepository;
import com.cdfive.search.service.AbstractSearchService;
import com.cdfive.search.service.LogRequestEsService;
import com.cdfive.search.vo.logrequest.QueryLogRequestDetailReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestDetailRespVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
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
public class LogRequestEsServiceImpl extends AbstractSearchService implements LogRequestEsService {

    @Autowired
    private LogRequestEsRepository logRequestEsRepository;

//    @Override
//    public void saveLogRequest(LogRequestMessageVo messageVo) {
//        LogRequestEo eo = new LogRequestEo();
//        eo.setId(CommonUtil.getTraceId());
//
//        eo.setTraceId(messageVo.getTraceId());
//
//        eo.setAppName(messageVo.getAppName());
//        eo.setAppIp(messageVo.getAppIp());
//        eo.setAppPort(messageVo.getAppPort());
//
//        eo.setRequestUri(messageVo.getRequestUri());
//        eo.setRemoteAddr(messageVo.getRemoteAddr());
//
//        eo.setCostMs(messageVo.getCostMs());
//        eo.setRequestBody(messageVo.getRequestBody());
//
//        eo.setExExist(messageVo.getExExist());
//        eo.setExClassName(messageVo.getExClassName());
//        eo.setExStackTrace(messageVo.getExStackTrace());
//
//        eo.setStartTime(messageVo.getStartTime());
//        eo.setCreateTime(messageVo.getCreateTime());
//        eo.setUpdateTime(new Date());
//        logRequestEsRepository.save(eo);
//    }

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

        String appName = reqVo.getAppName();
        if (StringUtils.isNotBlank(appName)) {
            rootQueryBuilder.filter(QueryBuilders.termQuery("appName", appName));
        }

        String ip = reqVo.getIp();
        if (StringUtils.isNotBlank(ip)) {
            rootQueryBuilder.filter(QueryBuilders.termQuery("appIp", ip));
        }

        Integer port = reqVo.getPort();
        if (port != null) {
            rootQueryBuilder.filter(QueryBuilders.termQuery("appPort", port));
        }

        String requestUri = reqVo.getRequestUri();
        if (StringUtils.isNotBlank(requestUri)) {
            rootQueryBuilder.filter(QueryBuilders.wildcardQuery("requestUri", "*" + requestUri + "*"));
        }

        Boolean success = reqVo.getSuccess();
        if (success != null) {
            rootQueryBuilder.filter(QueryBuilders.termQuery("exExist", !success));
        }

        SearchQuery searchQuery = new SearchQuery(rootQueryBuilder, PageRequest.of(reqVo.getPageNum() - 1, reqVo.getPageSize()));

        String sortField = reqVo.getSortField();
        String sortOrder = reqVo.getSortOrder();
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)) {
            if ("costMs".equals(sortField)) {
                searchQuery.withSort(SortBuilders.fieldSort("costMs").order("ascending".equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC));
            }
        } else {
            searchQuery.withSort(SortBuilders.fieldSort("startTime").order(SortOrder.DESC));
        }

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
                respVo.setSuccess(BooleanUtils.isNotTrue(eo.getExExist()));
                respVo.setExceptionClassName(eo.getExClassName());
                respVo.setStartTime(eo.getStartTime());
                respVo.setCreateTime(eo.getCreateTime());
                respVo.setUpdateTime(eo.getUpdateTime());
                return respVo;
            }
        });
    }

    @Override
    public QueryLogRequestDetailRespVo queryLogRequestDetail(QueryLogRequestDetailReqVo reqVo) {
        checkNotNull(reqVo, "请求参数不能为空");

        String id = reqVo.getId();
        checkNotBlank(id, "id不能为空");

        EsEntityVo<LogRequestEo> esEntityVo = logRequestEsRepository.findOne(id);
        checkNotNull(esEntityVo, "记录不存在,id=" + id);

        LogRequestEo eo = esEntityVo.getEntity();
        checkNotNull(eo, "记录不存在,id=" + id);

        QueryLogRequestDetailRespVo respVo = new QueryLogRequestDetailRespVo();
        respVo.setId(eo.getId());
        respVo.setTraceId(eo.getTraceId());
        respVo.setAppName(eo.getAppName());
        respVo.setAppIp(eo.getAppIp());
        respVo.setAppPort(eo.getAppPort());
        respVo.setRequestUri(eo.getRequestUri());
        respVo.setRemoteAddr(eo.getRemoteAddr());
        respVo.setCostMs(eo.getCostMs());
        respVo.setRequestBody(eo.getRequestBody());
        respVo.setSuccess(!eo.getExExist());
        respVo.setExceptionClassName(eo.getExClassName());
        respVo.setExceptionStackTrace(eo.getExStackTrace());
        respVo.setStartTime(eo.getStartTime());
        respVo.setCreateTime(eo.getCreateTime());
        respVo.setUpdateTime(eo.getUpdateTime());
        return respVo;
    }
}
