package com.cdfive.user.transformer;

import com.cdfive.user.po.RolePo;
import com.cdfive.user.vo.role.QueryRoleListPageRespVo;

import java.util.function.Function;

/**
 * @author cdfive
 */
public class QueryRoleListPageTransformer implements Function<RolePo, QueryRoleListPageRespVo> {

    public static QueryRoleListPageTransformer INSTANCE = new QueryRoleListPageTransformer();

    private QueryRoleListPageTransformer() {

    }

    @Override
    public QueryRoleListPageRespVo apply(RolePo po) {
        if (po == null) {
            return null;
        }

        QueryRoleListPageRespVo vo = new QueryRoleListPageRespVo();
        vo.setId(po.getId());
        vo.setName(po.getName());
        vo.setDescription(po.getDescription());
        vo.setEnable(po.getEnable());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }
}
