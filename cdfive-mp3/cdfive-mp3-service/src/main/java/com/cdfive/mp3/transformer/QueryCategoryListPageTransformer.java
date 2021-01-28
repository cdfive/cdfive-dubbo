package com.cdfive.mp3.transformer;

import com.cdfive.mp3.entity.po.CategoryPo;
import com.cdfive.mp3.vo.category.QueryCategoryListPageRespVo;

import java.util.function.Function;

/**
 * @author cdfive
 */
public class QueryCategoryListPageTransformer implements Function<CategoryPo, QueryCategoryListPageRespVo> {

    public static QueryCategoryListPageTransformer INSTANCE = new QueryCategoryListPageTransformer();

    private QueryCategoryListPageTransformer() {

    }

    @Override
    public QueryCategoryListPageRespVo apply(CategoryPo po) {
        if (po == null) {
            return null;
        }

        QueryCategoryListPageRespVo vo = new QueryCategoryListPageRespVo();
        vo.setId(po.getId());
        vo.setName(po.getCategoryName());
        vo.setDescription(po.getDescription());
        vo.setSort(po.getSort());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }
}
