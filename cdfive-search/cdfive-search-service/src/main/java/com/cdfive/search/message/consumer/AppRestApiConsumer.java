package com.cdfive.search.message.consumer;

import com.cdfive.common.util.JacksonUtil;
import com.cdfive.common.vo.AppRestApiContextVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author xiejihan
 * @date 2023-04-07
 */
@Slf4j
@Component
public class AppRestApiConsumer {

    @JmsListener(destination = "appRestApiQueue")
    public void receive(AppRestApiContextVo contextVo) {
        log.info(JacksonUtil.objToJson(contextVo));
    }
}
