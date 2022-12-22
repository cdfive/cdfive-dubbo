package com.cdfive.log.controller;

import com.cdfive.log.service.BizLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@RequestMapping("/bizlog")
@RestController
public class BizLogController {

    @Autowired
    private BizLogService bizLogService;

    @PostMapping("/addBizLog")
    public Integer addBizLog(String info, Integer keyId, String ip) {
        Integer result = bizLogService.addBizLog(info, keyId, ip);
        return result;
    }
}
