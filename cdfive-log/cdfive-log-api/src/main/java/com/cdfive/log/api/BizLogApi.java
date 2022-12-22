package com.cdfive.log.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author cdfive
 */
@FeignClient(name = "cdfive-log")
public interface BizLogApi {

    @PostMapping("/bizlog/addBizLog")
    Integer addBizLog(@RequestParam("info") String info, @RequestParam("keyId") Integer keyId, @RequestParam("ip") String ip);
}
