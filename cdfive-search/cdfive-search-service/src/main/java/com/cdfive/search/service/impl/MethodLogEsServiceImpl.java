package com.cdfive.search.service.impl;

import com.cdfive.search.eo.MethodLogEo;
import com.cdfive.search.repository.MethodLogEsRepository;
import com.cdfive.search.service.MethodLogEsService;
import com.cdfive.search.vo.methodlog.SaveMethodLogReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@Service("methodLogEsService")
public class MethodLogEsServiceImpl implements MethodLogEsService {

    @Autowired
    private MethodLogEsRepository methodLogEsRepository;

    @Override
    public void saveMethodLogs(List<SaveMethodLogReqVo> reqVos) {
        if (ObjectUtils.isEmpty(reqVos)) {
            return;
        }

        List<MethodLogEo> eos = reqVos.stream().map(o -> {
            MethodLogEo eo = new MethodLogEo();
            eo.setId(o.getId());
            eo.setIp(o.getIp());
            eo.setMethodName(o.getMethodName());
            eo.setRequestJson(o.getRequestJson());
            eo.setResponseJson(o.getResponseJson());
            eo.setSuccess(o.getSuccess());
            eo.setExceptionMessage(o.getExceptionMessage());
            eo.setExceptionStackTrace(o.getExceptionStackTrace());
            eo.setStartTime(o.getStartTime());
            eo.setEndTime(o.getEndTime());
            eo.setTimeCostMs(o.getTimeCostMs());
            eo.setCreateTime(o.getCreateTime());
            eo.setUpdateTime(o.getUpdateTime());
            eo.setDeleted(o.getDeleted());
            return eo;
        }).collect(Collectors.toList());
        methodLogEsRepository.save(eos);
    }
}
