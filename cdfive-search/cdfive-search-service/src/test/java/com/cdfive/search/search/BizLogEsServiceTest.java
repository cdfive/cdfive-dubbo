package com.cdfive.search.search;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.util.FastJsonUtil;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.es.constant.EsConstant;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.util.EsUtil;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.search.BaseTest;
import com.cdfive.search.eo.BizLogEo;
import com.cdfive.search.repository.BizLogEsRepository;
import com.cdfive.search.service.BizLogEsService;
import com.cdfive.search.vo.bizlog.QueryBizLogPageReqVo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;
//import org.junit.jupiter.api.Test;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    /**Test basic query start*/
    @Test
    public void testTerm() {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        TermQueryBuilder termQuery = QueryBuilders.termQuery("keyId", 6);
        rootQuery.filter(termQuery);
        SearchQuery searchQuery = new SearchQuery(rootQuery, PageRequest.of(0, 10));
        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        System.out.println(JSON.toJSONString(page));
        System.out.println(EsUtil.genDsl(searchQuery));
    }

    @Test
    public void testTerms() {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        TermsQueryBuilder termsQuery = QueryBuilders.termsQuery("keyId", new int[]{6, 24});
        rootQuery.filter(termsQuery);
        SearchQuery searchQuery = new SearchQuery(rootQuery, PageRequest.of(0, 10));
        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        System.out.println(JSON.toJSONString(page));
        System.out.println(EsUtil.genDsl(searchQuery));
    }

    @Test
    public void testMust() {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        rootQuery.must(QueryBuilders.termQuery("keyId", 6));
        SearchQuery searchQuery = new SearchQuery(rootQuery, PageRequest.of(0, 10));
        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        System.out.println(JSON.toJSONString(page));
        System.out.println(EsUtil.genDsl(searchQuery));
    }

    @Test
    public void testMust2() {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        rootQuery.filter(boolQuery);
        boolQuery.must(QueryBuilders.termQuery("keyId", 6));
        SearchQuery searchQuery = new SearchQuery(rootQuery, PageRequest.of(0, 10));
        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        System.out.println(JSON.toJSONString(page));
        System.out.println(EsUtil.genDsl(searchQuery));
    }

    @Test
    public void testMustNot() {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        rootQuery.mustNot(QueryBuilders.termQuery("keyId", 6));
        SearchQuery searchQuery = new SearchQuery(rootQuery, PageRequest.of(0, 10));
        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        System.out.println(JSON.toJSONString(page));
        System.out.println(EsUtil.genDsl(searchQuery));
    }

    @Test
    public void testShould() {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        rootQuery.should(QueryBuilders.termQuery("keyId", 6));
        rootQuery.should(QueryBuilders.termQuery("keyId", 21));
        SearchQuery searchQuery = new SearchQuery(rootQuery, PageRequest.of(0, 10));
        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        System.out.println(JSON.toJSONString(page));
        System.out.println(EsUtil.genDsl(searchQuery));
    }

    @Test
    public void testQueryString() {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("播").defaultField("info").analyzer(EsConstant.ANALYZER_IK_SMART).minimumShouldMatch("2<70%").boost(2);
        rootQuery.filter(queryStringQueryBuilder);
        SearchQuery searchQuery = new SearchQuery(rootQuery, PageRequest.of(0, 10));
        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        System.out.println(JSON.toJSONString(page));
        System.out.println(EsUtil.genDsl(searchQuery));
    }

    @Test
    public void testRange() {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("id").gt(10).lte(25);
        rootQuery.filter(rangeQuery);
        SearchQuery searchQuery = new SearchQuery(rootQuery, PageRequest.of(0, 10));
        Page<EsEntityVo<BizLogEo>> page = bizLogEsRepository.search(searchQuery);
        System.out.println(JSON.toJSONString(page));
        System.out.println(EsUtil.genDsl(searchQuery));
    }
    /**Test basic query end*/
}
