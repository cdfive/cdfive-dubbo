package com.cdfive.search.controller;

import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.search.api.LogRequestApi;
import com.cdfive.search.service.LogRequestEsService;
import com.cdfive.search.vo.logrequest.QueryLogRequestDetailReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestDetailRespVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageReqVo;
import com.cdfive.search.vo.logrequest.QueryLogRequestPageRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
@Slf4j
@RestController
public class LogRequestController implements LogRequestApi {

    @Autowired
    private LogRequestEsService logRequestEsService;

    @Override
    public String test() {
        log.info("search test start");
        int cost = ThreadLocalRandom.current().nextInt(5_000, 10_000);
        try {
            TimeUnit.MILLISECONDS.sleep(cost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("search test success,cost={}ms", cost);
        return "search test,cost=" + cost + "ms";
    }

    @Override
    public PageRespVo<QueryLogRequestPageRespVo> queryLogRequestPage(QueryLogRequestPageReqVo reqVo) {
        return logRequestEsService.queryLogRequestPage(reqVo);
    }

    @Override
    public QueryLogRequestDetailRespVo queryLogRequestDetail(QueryLogRequestDetailReqVo reqVo) {
        return logRequestEsService.queryLogRequestDetail(reqVo);
    }
}
