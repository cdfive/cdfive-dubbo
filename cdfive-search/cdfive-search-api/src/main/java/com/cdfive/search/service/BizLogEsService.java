package com.cdfive.search.service;

import com.cdfive.search.vo.SaveBizLogReqVo;

import java.util.List;

/**
 * @author cdfive
 */
public interface BizLogEsService {

    void saveBizLogs(List<SaveBizLogReqVo> reqVos);
}
