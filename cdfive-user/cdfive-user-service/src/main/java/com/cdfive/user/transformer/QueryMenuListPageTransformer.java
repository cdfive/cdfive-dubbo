package com.cdfive.user.transformer;

import com.cdfive.user.po.MenuPo;
import com.cdfive.user.vo.menu.QueryMenuListPageRespVo;

import java.util.function.Function;

/**
 * @author cdfive
 */
public class QueryMenuListPageTransformer implements Function<MenuPo, QueryMenuListPageRespVo> {

    public static QueryMenuListPageTransformer INSTANCE = new QueryMenuListPageTransformer();

    private QueryMenuListPageTransformer() {

    }

    @Override
    public QueryMenuListPageRespVo apply(MenuPo po) {
        if (po == null) {
            return null;
        }

        QueryMenuListPageRespVo vo = new QueryMenuListPageRespVo();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setDescription(po.getDescription());
        vo.setUrl(po.getUrl());
        vo.setIcon(po.getIcon());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }
}
