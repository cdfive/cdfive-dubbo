package com.cdfive.log.message.consumer;

import com.alibaba.fastjson.JSON;
import com.cdfive.log.po.MethodLogPo;
import com.cdfive.log.repository.MethodLogRepository;
import com.cdfive.log.vo.AddMethodLogVo;
import com.cdfive.search.service.MethodLogEsService;
import com.cdfive.search.vo.methodlog.SaveMethodLogReqVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class MethodLogConsumer {

    @Autowired
    private MethodLogEsService methodLogEsService;

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

        List<SaveMethodLogReqVo> saveMethodLogReqVos = Lists.newArrayList();
        SaveMethodLogReqVo saveMethodLogReqVo = new SaveMethodLogReqVo();
        saveMethodLogReqVos.add(saveMethodLogReqVo);
        saveMethodLogReqVo.setId(po.getId());
        saveMethodLogReqVo.setIp(po.getIp());
        saveMethodLogReqVo.setMethodName(po.getMethodName());
        saveMethodLogReqVo.setRequestJson(po.getRequestJson());
        saveMethodLogReqVo.setResponseJson(po.getResponseJson());
        saveMethodLogReqVo.setSuccess(po.getSuccess());
        saveMethodLogReqVo.setExceptionMessage(po.getExceptionMessage());
        saveMethodLogReqVo.setExceptionStackTrace(po.getExceptionStackTrace());
        saveMethodLogReqVo.setStartTime(po.getStartTime());
        saveMethodLogReqVo.setEndTime(po.getEndTime());
        saveMethodLogReqVo.setTimeCostMs(po.getTimeCostMs());
        saveMethodLogReqVo.setCreateTime(po.getCreateTime());
        saveMethodLogReqVo.setUpdateTime(po.getUpdateTime());
        saveMethodLogReqVo.setDeleted(po.getDeleted());
        methodLogEsService.saveMethodLogs(saveMethodLogReqVos);
    }
}
