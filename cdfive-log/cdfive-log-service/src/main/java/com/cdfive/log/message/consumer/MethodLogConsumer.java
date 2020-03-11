package com.cdfive.log.message.consumer;

import com.alibaba.fastjson.JSON;
import com.cdfive.log.po.MethodLogPo;
import com.cdfive.log.repository.MethodLogRepository;
import com.cdfive.log.vo.AddMethodLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class MethodLogConsumer {

    @Autowired
    private MethodLogRepository methodLogRepository;

    @JmsListener(destination = "methodLogQueue")
    public void receive(AddMethodLogVo vo) {
        log.info("MethodLogConsumer receive={}", JSON.toJSONString(vo));

        MethodLogPo po = new MethodLogPo();
        po.setIp(vo.getIp());
        po.setMethodName(vo.getMethodName());
        po.setRequestJson(vo.getRequestJson());
        po.setResponseJson(vo.getResponseJson());
        po.setSuccess(vo.getSuccess());
        po.setExceptionMessage(vo.getExceptionMessage());
        po.setExceptionStackTrace(vo.getExceptionStackTrace());
        po.setStartTime(vo.getStartTime());
        po.setEndTime(vo.getEndTime());
        po.setTimeCostMs(vo.getTimeCostMs());
        po.setCreateTime(new Date());
        po.setDeleted(false);
        methodLogRepository.save(po);
    }
}
