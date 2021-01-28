package com.cdfive.search.service;

import com.cdfive.search.vo.methodlog.SaveMethodLogReqVo;

import java.util.List;

/**
 * @author cdfive
 */
public interface MethodLogEsService {

    void saveMethodLogs(List<SaveMethodLogReqVo> reqVos);
}
