package com.cdfive.user.transformer;

import com.cdfive.user.po.AdminPo;
import com.cdfive.user.vo.admin.QueryAdminListPageRespVo;

import java.util.function.Function;

/**
 * @author cdfive
 */
public class QueryAdminListPageTransformer implements Function<AdminPo, QueryAdminListPageRespVo> {

    public static QueryAdminListPageTransformer INSTANCE = new QueryAdminListPageTransformer();

    private QueryAdminListPageTransformer() {

    }

    @Override
    public QueryAdminListPageRespVo apply(AdminPo po) {
        if (po == null) {
            return null;
        }

        QueryAdminListPageRespVo vo = new QueryAdminListPageRespVo();
        vo.setId(po.getId());
        vo.setUsername(po.getUsername());
        vo.setPassword(po.getPassword());
        vo.setAliasname(po.getAliasname());
        vo.setMobile(po.getMobile());
        vo.setStatus(po.getStatus());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return vo;
    }
}
