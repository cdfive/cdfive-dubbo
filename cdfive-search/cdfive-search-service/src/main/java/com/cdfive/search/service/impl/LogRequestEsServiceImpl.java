package com.cdfive.search.service.impl;

import com.cdfive.common.util.CommonUtil;
import com.cdfive.framework.message.vo.LogRequestMessageVo;
import com.cdfive.search.eo.LogRequestEo;
import com.cdfive.search.repository.LogRequestEsRepository;
import com.cdfive.search.service.LogRequestEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author cdfive
 */
@Slf4j
@Service("logRequestEsService")
public class LogRequestEsServiceImpl implements LogRequestEsService {

    @Autowired
    private LogRequestEsRepository appRestApiLogEsRepository;

    @Override
    public void saveLogRequest(LogRequestMessageVo messageVo) {
        LogRequestEo eo = new LogRequestEo();
        eo.setId(CommonUtil.getTraceId());

        eo.setTraceId(messageVo.getTraceId());

        eo.setAppName(messageVo.getAppName());
        eo.setAppPort(messageVo.getAppPort());

        eo.setRequestUri(messageVo.getRequestUri());
        eo.setRemoteAddr(messageVo.getRemoteAddr());

        eo.setCostMs(messageVo.getCostMs());
        eo.setRequestBody(messageVo.getRequestBody());

        eo.setExExist(messageVo.getExExist());
        eo.setExClassName(messageVo.getExClassName());
        eo.setExStackTrace(messageVo.getExStackTrace());

        eo.setStartTime(messageVo.getStartTime());
        eo.setCreateTime(messageVo.getCreateTime());
        eo.setUpdateTime(new Date());
        appRestApiLogEsRepository.save(eo);
    }
}
