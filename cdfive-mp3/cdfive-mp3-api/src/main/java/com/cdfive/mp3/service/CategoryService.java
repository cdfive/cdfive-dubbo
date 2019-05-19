package com.cdfive.mp3.service;

import com.cdfive.mp3.vo.category.AddCategoryReqVo;
import com.cdfive.mp3.vo.category.UpdateCategoryReqVo;

import java.util.List;

/**
 * @author cdfive
 */
public interface CategoryService {

    Integer addCategory(AddCategoryReqVo reqVo);

    void updateCategory(UpdateCategoryReqVo reqVo);

    void deleteCategory(List<Integer> ids);
}
