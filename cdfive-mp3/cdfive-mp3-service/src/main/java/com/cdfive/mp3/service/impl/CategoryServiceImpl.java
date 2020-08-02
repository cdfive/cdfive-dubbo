package com.cdfive.mp3.service.impl;

import com.cdfive.log.service.BizLogService;
import com.cdfive.mp3.entity.po.CategoryPo;
import com.cdfive.mp3.repository.db.CategoryRepository;
import com.cdfive.mp3.service.CategoryService;
import com.cdfive.mp3.service.AbstractMp3Service;
import com.cdfive.mp3.vo.category.AddCategoryReqVo;
import com.cdfive.mp3.vo.category.UpdateCategoryReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author cdfive
 */
@Service("categoryService")
public class CategoryServiceImpl extends AbstractMp3Service implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BizLogService bizLogService;

    @Override
    public Integer addCategory(AddCategoryReqVo reqVo) {
//        bizLogService.addBizLog(JSON.toJSONString(reqVo));

        String name = reqVo.getName();
        checkNotNull(name, "分类名称不能为空");

        CategoryPo categoryPo = new CategoryPo();
        categoryPo.setCategoryName(name);
        categoryPo.setDescription(reqVo.getDescription());
        categoryPo.setSort(reqVo.getSort());
        categoryPo.setCreateTime(new Date());
        categoryPo.setUpdateTime(new Date());
        categoryPo.setDeleted(false);
        categoryRepository.save(categoryPo);

        return categoryPo.getId();
    }

    @Override
    public void updateCategory(UpdateCategoryReqVo reqVo) {

    }

    @Override
    public void deleteCategory(List<Integer> ids) {

    }


}
