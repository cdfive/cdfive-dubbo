package com.cdfive.search.service;

import com.cdfive.common.vo.AppRestApiLogContextVo;

/**
 * @author xiejihan
 * @date 2023-04-10
 */
public interface AppRestApiLogEsService {

    void saveAppRestApiLog(AppRestApiLogContextVo contextVo);
}
