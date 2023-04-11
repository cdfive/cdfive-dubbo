package com.cdfive.search.service.impl;

import com.cdfive.common.vo.AppRestApiLogContextVo;
import com.cdfive.search.eo.AppRestApiLogEo;
import com.cdfive.search.repository.AppRestApiLogEsRepository;
import com.cdfive.search.service.AppRestApiLogEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xiejihan
 * @date 2023-04-10
 */
@Slf4j
@Service("appRestApiLogEsService")
public class AppRestApiLogEsServiceImpl implements AppRestApiLogEsService {

    @Autowired
    private AppRestApiLogEsRepository appRestApiLogEsRepository;

    @Override
    public void saveAppRestApiLog(AppRestApiLogContextVo contextVo) {
        AppRestApiLogEo eo = new AppRestApiLogEo();
        eo.setId(contextVo.getTraceId());
        eo.setAppName(contextVo.getAppName());
        eo.setServerPort(contextVo.getServerPort());
        eo.setRequestUri(contextVo.getRequestUri());
        eo.setRemoteAddr(contextVo.getRemoteAddr());
        eo.setCostMs(contextVo.getCostMs());
        eo.setRequestBody(contextVo.getRequestBody());
        eo.setExClassName(contextVo.getExClassName());
        eo.setExStackTrace(contextVo.getExStackTrace());
        eo.setCreateTime(contextVo.getCreateTime());
        eo.setUpdateTime(new Date());
        appRestApiLogEsRepository.save(eo);
    }
}
