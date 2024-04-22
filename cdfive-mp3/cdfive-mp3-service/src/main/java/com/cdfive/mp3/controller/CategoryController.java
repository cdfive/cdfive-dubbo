package com.cdfive.mp3.controller;

import com.cdfive.common.vo.IntegerIdNameVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.api.CategoryApi;
import com.cdfive.mp3.service.CategoryService;
import com.cdfive.mp3.vo.category.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cdfive
 */
@Slf4j
@RestController
public class CategoryController implements CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<IntegerIdNameVo> findTopCategories() {
        return categoryService.findTopCategories();
    }

    @Override
    public PageRespVo<QueryCategoryListPageRespVo> queryCategoryListPage(QueryCategoryListPageReqVo reqVo) {
        return categoryService.queryCategoryListPage(reqVo);
    }

    @Override
    public QueryCategoryDetailRespVo queryCategoryDetail(QueryCategoryDetailReqVo reqVo) {
        return categoryService.queryCategoryDetail(reqVo);
    }

    @Override
    public Integer addCategory(AddCategoryReqVo reqVo) {
        return categoryService.addCategory(reqVo);
    }

    @Override
    public void updateCategory(UpdateCategoryReqVo reqVo) {
        categoryService.updateCategory(reqVo);
    }

    @Override
    public void deleteCategory(DeleteCategoryReqVo reqVo) {
        categoryService.deleteCategory(reqVo);
    }


}
