package com.cdfive.api.controller;

import com.cdfive.common.api.ApiResponse;
import com.cdfive.common.base.AbstractController;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.search.api.LogRequestApi;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@RestController
public class SearchController extends AbstractController {

    @Autowired
    private LogRequestApi logRequestApi;

    @RequestMapping("/api/v1/logRequest/list")
    public ApiResponse<PageRespVo<QueryLogRequestPageRespVo>> logRequestList(@RequestBody(required = false) QueryLogRequestPageReqVo reqVo) {
        if (reqVo == null) {
            reqVo = new QueryLogRequestPageReqVo();
        }
        return succ(logRequestApi.queryLogRequestPage(reqVo));
    }
}
