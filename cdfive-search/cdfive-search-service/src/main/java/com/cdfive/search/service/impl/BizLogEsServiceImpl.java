package com.cdfive.search.service.impl;

import com.cdfive.common.vo.page.PageRespVo;
import com.cdfive.search.eo.BizLogEo;
import com.cdfive.search.repository.BizLogEsRepository;
import com.cdfive.search.service.BizLogEsService;
import com.cdfive.search.service.core.BizLogSearchProcessor;
import com.cdfive.search.transformer.QueryBizLogPageTransformer;
import com.cdfive.search.vo.bizlog.QueryBizLogPageReqVo;
import com.cdfive.search.vo.bizlog.QueryBizLogPageRespVo;
import com.cdfive.search.vo.bizlog.SaveBizLogReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@Service("bizLogEsService")
public class BizLogEsServiceImpl implements BizLogEsService {

    @Autowired
    private BizLogEsRepository bizLogEsRepository;

    @Autowired
    private QueryBizLogPageTransformer queryBizLogPageTransformer;

    @Autowired
    private BizLogSearchProcessor bizLogSearchProcessor;

    @Override
    public void saveBizLogs(List<SaveBizLogReqVo> reqVos) {
        if (ObjectUtils.isEmpty(reqVos)) {
            return;
        }

        List<BizLogEo> eos = reqVos.stream().map(o -> {
            BizLogEo eo = new BizLogEo();
            BeanUtils.copyProperties(o, eo);
            return eo;
        }).collect(Collectors.toList());
        bizLogEsRepository.save(eos);
    }

    @Override
    public PageRespVo<QueryBizLogPageRespVo> queryBizLogPage(QueryBizLogPageReqVo reqVo) {
        return bizLogSearchProcessor.queryBizLogPage(reqVo);
    }
}
