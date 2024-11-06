package com.cdfive.log.api;

import com.cdfive.log.vo.AddMethodLogVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author cdfive
 */
@FeignClient(name = "cdfive-log")
public interface BizLogApi {

    @PostMapping("/bizlog/addBizLog")
    Integer addBizLog(@RequestParam("info") String info, @RequestParam("keyId") Integer keyId, @RequestParam("ip") String ip);

    @PostMapping("/bizlog/addMethodLog")
    AddMethodLogVo addMethodLog(@RequestBody AddMethodLogVo addMethodLogVo);
}
