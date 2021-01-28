package com.cdfive.mp3.service;

import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.mp3.vo.category.*;

import java.util.List;

/**
 * @author cdfive
 */
public interface CategoryService {

    PageRespVo<QueryCategoryListPageRespVo> queryCategoryListPage(QueryCategoryListPageReqVo reqVo);

    BootstrapPageRespVo<QueryCategoryListPageRespVo> queryCategoryListBootstrapPage(QueryCategoryListPageReqVo reqVo);

    FindCategoryDetailVo findCategoryDetail(Integer id);

    Integer addCategory(AddCategoryReqVo reqVo);

    void updateCategory(UpdateCategoryReqVo reqVo);

    void deleteCategory(List<Integer> ids);
}
