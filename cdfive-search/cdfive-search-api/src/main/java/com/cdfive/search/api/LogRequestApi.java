package com.cdfive.search.api;

import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestDetailReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestDetailRespVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cdfive
 */
@FeignClient(name = "cdfive-search")
public interface LogRequestApi {

    @RequestMapping("/api/v1/search/logRequest/list")
    PageRespVo<QueryLogRequestPageRespVo> queryLogRequestPage(@RequestBody(required = false) QueryLogRequestPageReqVo reqVo);

    @RequestMapping("/api/v1/search/logRequest/detail")
    QueryLogRequestDetailRespVo queryLogRequestDetail(@RequestBody(required = false) QueryLogRequestDetailReqVo reqVo);
}
