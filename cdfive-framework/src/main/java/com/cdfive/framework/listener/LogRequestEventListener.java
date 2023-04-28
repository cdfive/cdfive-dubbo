package com.cdfive.framework.listener;

import com.cdfive.framework.listener.event.LogRequestEvent;
import com.cdfive.framework.message.producer.LogRequestProducer;
import com.cdfive.framework.message.vo.LogRequestMessageVo;
import com.cdfive.framework.properties.AppProperties;
import com.cdfive.framework.util.CommonUtil;
import com.cdfive.framework.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author cdfive
 */
@Component
public class LogRequestEventListener implements ApplicationListener<LogRequestEvent> {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private LogRequestProducer logRequestProducer;

    @Override
    public void onApplicationEvent(LogRequestEvent event) {
        LogRequestMessageVo messageVo = buildMessageVo(event);
        logRequestProducer.send(messageVo);
    }

    public LogRequestMessageVo buildMessageVo(LogRequestEvent event) {
        HttpServletRequest request = event.getRequest();
        Throwable ex = event.getEx();

        LogRequestMessageVo messageVo = new LogRequestMessageVo();
        messageVo.setTraceId(ServletUtil.getRequestTraceId(request));

        messageVo.setAppName(appProperties.getAppName());
        // TODO
//        messageVo.setAppIp();
        messageVo.setAppPort(appProperties.getAppPort());

        messageVo.setRequestUri(request.getRequestURI());
        messageVo.setRemoteAddr(request.getRemoteAddr());

        messageVo.setCostMs(ServletUtil.getRequestCostTimeMs(request));

        messageVo.setRequestBody(ServletUtil.getRequestBody(request));

        messageVo.setExExist(ex != null);
        if (ex != null) {
            messageVo.setExClassName(ex.getClass().getName());
            messageVo.setExStackTrace(CommonUtil.getStackTraceAsString(ex));
        }

        Long startTime = ServletUtil.getRequestStartTime(request);
        if (startTime != null) {
            messageVo.setStartTime(new Date(startTime));
        }
        messageVo.setCreateTime(new Date());
        return messageVo;
    }
}
