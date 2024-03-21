package com.cdfive.search.service;

import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.framework.message.vo.LogRequestMessageVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestDetailReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestDetailRespVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageRespVo;

/**
 * @author cdfive
 */
public interface LogRequestEsService {

    void saveLogRequest(LogRequestMessageVo messageVo);

    PageRespVo<QueryLogRequestPageRespVo> queryLogRequestPage(QueryLogRequestPageReqVo reqVo);

    QueryLogRequestDetailRespVo queryLogRequestDetail(QueryLogRequestDetailReqVo reqVo);
}
