package com.cdfive.mp3.api;

import com.cdfive.common.vo.IntegerIdNameVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.vo.category.QueryCategoryListPageReqVo;
import com.cdfive.mp3.vo.category.QueryCategoryListPageRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author cdfive
 */
@FeignClient(name = "cdfive-mp3")
public interface CategoryApi {

    @RequestMapping("/api/v1/mp3/category/topList")
    List<IntegerIdNameVo> findTopCategories();

    @RequestMapping("/api/v1/mp3/category/list")
    PageRespVo<QueryCategoryListPageRespVo> queryCategoryListPage(@RequestBody(required = false) QueryCategoryListPageReqVo reqVo);
}
