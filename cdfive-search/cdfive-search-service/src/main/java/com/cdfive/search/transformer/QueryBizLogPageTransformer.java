package com.cdfive.search.transformer;

import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.search.eo.BizLogEo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author cdfive
 */
@Component
public class QueryBizLogPageTransformer implements Function<EsEntityVo<BizLogEo>, QueryBizLogPageRespVo> {

    @Override
    public QueryBizLogPageRespVo apply(EsEntityVo<BizLogEo> vo) {
        if (vo == null) {
            return null;
        }

        BizLogEo eo = vo.getEntity();
        if (eo == null) {
            return null;
        }

        QueryBizLogPageRespVo respVo = new QueryBizLogPageRespVo();
        respVo.setId(eo.getId());
        respVo.setInfo(eo.getInfo());
        respVo.setKeyId(eo.getKeyId());
        respVo.setIp(eo.getIp());
        respVo.setCreateTime(eo.getCreateTime());
        respVo.setUpdateTime(eo.getUpdateTime());
        respVo.setDeleted(eo.getDeleted());
        return respVo;
    }
}
