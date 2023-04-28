package com.cdfive.search.message.consumer;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.framework.message.vo.LogRequestMessageVo;
import com.cdfive.search.service.LogRequestEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class LogRequestConsumer {

    @Autowired
    private LogRequestEsService appRestApiLogEsService;

    @JmsListener(destination = "logRequestQueue")
    public void receive(LogRequestMessageVo messageVo) {
        if (log.isDebugEnabled()) {
            log.debug(JacksonUtil.objToJson(messageVo));
        }

        appRestApiLogEsService.saveLogRequest(messageVo);
    }
}
