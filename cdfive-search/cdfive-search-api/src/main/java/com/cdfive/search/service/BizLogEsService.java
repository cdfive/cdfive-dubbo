package com.cdfive.search.service;

import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageReqVo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;
import com.cdfive.search.vo.bizlog.SaveBizLogReqVo;

import java.util.List;

/**
 * @author cdfive
 */
public interface BizLogEsService {

    void saveBizLogs(List<SaveBizLogReqVo> reqVos);

    PageRespVo<QueryBizLogPageRespVo> queryBizLogPage(QueryBizLogPageReqVo reqVo);
}
