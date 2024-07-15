package com.cdfive.mp3.api;

import com.cdfive.common.vo.IntegerIdNameVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.vo.category.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author cdfive
 */
//@FeignClient(name = "cdfive-mp3")
public interface CategoryApi {

    @RequestMapping("/api/v1/mp3/category/topList")
    List<IntegerIdNameVo> findTopCategories();

    @RequestMapping("/api/v1/mp3/category/list")
    PageRespVo<QueryCategoryListPageRespVo> queryCategoryListPage(@RequestBody(required = false) QueryCategoryListPageReqVo reqVo);

    @PostMapping("/api/v1/mp3/category/detail")
    QueryCategoryDetailRespVo queryCategoryDetail(@RequestBody(required = false) QueryCategoryDetailReqVo reqVo);

    @PostMapping("/api/v1/mp3/category/add")
    Integer addCategory(@RequestBody(required = false) AddCategoryReqVo reqVo);

    @PostMapping("/api/v1/mp3/category/update")
    void updateCategory(@RequestBody(required = false) UpdateCategoryReqVo reqVo);

    @PostMapping("/api/v1/mp3/category/delete")
    void deleteCategory(@RequestBody(required = false) DeleteCategoryReqVo reqVo);
}
