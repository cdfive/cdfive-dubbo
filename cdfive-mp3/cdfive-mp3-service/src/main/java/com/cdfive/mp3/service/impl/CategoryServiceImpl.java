package com.cdfive.mp3.service.impl;

import com.cdfive.common.util.JpaPageUtil;
import com.cdfive.common.vo.IntegerIdNameVo;
import com.cdfive.common.vo.page.BootstrapPageRespVo;
import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.log.service.BizLogService;
import com.cdfive.mp3.entity.po.CategoryPo;
import com.cdfive.mp3.repository.db.CategoryRepository;
import com.cdfive.mp3.repository.db.specification.QueryCategorySpecification;
import com.cdfive.mp3.service.AbstractMp3Service;
import com.cdfive.mp3.service.CategoryService;
import com.cdfive.mp3.transformer.QueryCategoryListPageTransformer;
import com.cdfive.mp3.vo.category.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Service("categoryService")
public class CategoryServiceImpl extends AbstractMp3Service implements CategoryService {

    @Autowired
    private BizLogService bizLogService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PageRespVo<QueryCategoryListPageRespVo> queryCategoryListPage(QueryCategoryListPageReqVo reqVo) {
        return JpaPageUtil.buildPage(reqVo, categoryRepository, new QueryCategorySpecification(reqVo), QueryCategoryListPageTransformer.INSTANCE);
    }

    @Override
    public BootstrapPageRespVo<QueryCategoryListPageRespVo> queryCategoryListBootstrapPage(QueryCategoryListPageReqVo reqVo) {
        return JpaPageUtil.buildBootstrapPage(reqVo, categoryRepository, new QueryCategorySpecification(reqVo), QueryCategoryListPageTransformer.INSTANCE);
    }

    @Override
    public FindCategoryDetailVo findCategoryDetail(Integer id) {
        checkNotNull(id, "id不能为空");

        CategoryPo categoryPo = categoryRepository.findById(id).orElseThrow(() -> exception("记录不存在,id=" + id));

        FindCategoryDetailVo detailVo = new FindCategoryDetailVo();
        detailVo.setId(categoryPo.getId());
        detailVo.setName(categoryPo.getCategoryName());
        detailVo.setDescription(categoryPo.getDescription());
        detailVo.setSort(categoryPo.getSort());
        detailVo.setCreateTime(categoryPo.getCreateTime());
        detailVo.setUpdateTime(categoryPo.getUpdateTime());
        return detailVo;
    }

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
        checkNotNull(reqVo, "请求参数不能为空");

        Integer id = reqVo.getId();
        checkNotNull(id, "记录id不能为空");

        CategoryPo categoryPo = categoryRepository.findById(id).orElseThrow(() -> exception("记录不存在,id=" + id));

        String name = reqVo.getName();
        checkNotNull(name, "分类名称不能为空");
        categoryPo.setCategoryName(name);

        String description = reqVo.getDescription();
        categoryPo.setDescription(description);

        Integer sort = reqVo.getSort();
        categoryPo.setSort(sort);

        categoryPo.setUpdateTime(now());
        categoryRepository.save(categoryPo);
    }

    @Override
    public void deleteCategory(List<Integer> ids) {
        checkNotEmpty(ids, "记录id列表不能为空");

        List<CategoryPo> categoryPos = categoryRepository.findAllById(ids);
        checkNotEmpty(categoryPos, "记录不存在,ids=" + ids);

        categoryPos.forEach(o -> {
            if (o.getDeleted()) {
                fail("记录已删除,id=" + o.getId());
            }
            o.setDeleted(true);
            o.setUpdateTime(now());
        });

        categoryRepository.saveAll(categoryPos);
    }

    @Override
    public List<IntegerIdNameVo> findTopCategories() {
        List<CategoryPo> pos = categoryRepository.findListByDeleted(Boolean.FALSE);
        return pos.stream().map(o -> new IntegerIdNameVo(o.getId(), o.getCategoryName())).collect(Collectors.toList());
    }
}
