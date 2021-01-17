package com.cdfive.search.search;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.util.FastJsonUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.search.BaseTest;
import com.cdfive.search.eo.BizLogEo;
import com.cdfive.search.repository.BizLogEsRepository;
import com.cdfive.search.service.BizLogEsService;
import com.cdfive.search.vo.bizlog.QueryBizLogPageReqVo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cdfive
 */
public class BizLogEsServiceTest extends BaseTest {

    @Autowired
    private BizLogEsService bizLogEsService;

    @Autowired
    private BizLogEsRepository bizLogEsRepository;

    @Test
    public void testSave() {
        String json = "{\"createTime\":1555236597000,\"deleted\":false,\"id\":1,\"info\":\"播放mp3\",\"ip\":\"171.217.21.141\",\"keyId\":6,\"updateTime\":1555236597000}";
        BizLogEo eo = FastJsonUtil.json2Obj(json, BizLogEo.class);
        bizLogEsRepository.save(eo);
    }

    @Test
    public void testDelete() {
        bizLogEsRepository.delete(1);
    }

    @Test
    public void testQueryBizLogPage() {
        QueryBizLogPageReqVo reqVo = new QueryBizLogPageReqVo();
        PageRespVo<QueryBizLogPageRespVo> respVo = bizLogEsService.queryBizLogPage(reqVo);
        System.out.println(JSON.toJSONString(respVo));
    }
}
