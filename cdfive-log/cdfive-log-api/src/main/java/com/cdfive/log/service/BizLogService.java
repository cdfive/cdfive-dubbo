package com.cdfive.log.service;

/**
 * @author cdfive
 */
public interface BizLogService {

    Integer addBizLog(String info, Integer keyId, String ip);
}
