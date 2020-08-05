package com.cdfive.search.search;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.search.BaseTest;
import com.cdfive.search.service.BizLogEsService;
import com.cdfive.search.vo.bizlog.QueryBizLogPageReqVo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cdfive
 */
public class BizLogEsServiceTest extends BaseTest {

    @Autowired
    private BizLogEsService bizLogEsService;

    @Test
    public void testQueryBizLogPage() {
        QueryBizLogPageReqVo reqVo = new QueryBizLogPageReqVo();
        PageRespVo<QueryBizLogPageRespVo> respVo = bizLogEsService.queryBizLogPage(reqVo);
        System.out.println(JSON.toJSONString(respVo));
    }
}
