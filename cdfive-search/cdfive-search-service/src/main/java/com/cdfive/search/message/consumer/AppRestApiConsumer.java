package com.cdfive.search.message.consumer;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.AppRestApiLogContextVo;
import com.cdfive.search.service.AppRestApiLogEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author xiejihan
 * @date 2023-04-07
 */
@Slf4j
@Component
public class AppRestApiConsumer {

    @Autowired
    private AppRestApiLogEsService appRestApiLogEsService;

    @JmsListener(destination = "appRestApiLogQueue")
    public void receive(AppRestApiLogContextVo contextVo) {
        if (log.isInfoEnabled()) {
            log.info(JacksonUtil.objToJson(contextVo));
        }

        appRestApiLogEsService.saveAppRestApiLog(contextVo);
    }
}
