package com.cdfive.search.service;

import com.cdfive.framework.message.vo.LogRequestMessageVo;

/**
 * @author cdfive
 */
public interface LogRequestEsService {

    void saveLogRequest(LogRequestMessageVo messageVo);
}
