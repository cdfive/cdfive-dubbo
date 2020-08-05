package com.cdfive.search.transformer;

import com.cdfive.search.eo.BizLogEo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;

import java.util.function.Function;

/**
 * @author cdfive
 */
public class QueryBizLogPageTransformer implements Function<BizLogEo, QueryBizLogPageRespVo> {

    public static QueryBizLogPageTransformer INSTANCE = new QueryBizLogPageTransformer();

    private QueryBizLogPageTransformer() {

    }

    @Override
    public QueryBizLogPageRespVo apply(BizLogEo eo) {
        if (eo == null) {
            return null;
        }

        QueryBizLogPageRespVo vo = new QueryBizLogPageRespVo();
        vo.setId(eo.getId());
        vo.setInfo(eo.getInfo());
        vo.setKeyId(eo.getKeyId());
        vo.setIp(eo.getIp());
        vo.setCreateTime(eo.getCreateTime());
        vo.setUpdateTime(eo.getUpdateTime());
        vo.setDeleted(eo.getDeleted());
        return vo;
    }
}
