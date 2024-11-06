package com.cdfive.log.controller;

import com.cdfive.log.api.BizLogApi;
import com.cdfive.log.service.BizLogService;
import com.cdfive.log.vo.AddMethodLogVo;
import com.cdfive.springboot.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
@Slf4j
//@RequestMapping("/bizlog")
@RequestMapping("/")
@RestController
public class BizLogController implements BizLogApi {

    @Autowired
    private BizLogService bizLogService;

    @Override
    public Integer addBizLog(String info, Integer keyId, String ip) {
        log.info("addBizLog start,info={},keyId={},ip={}", info, keyId, ip);
        long start = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("addBizLog success,cost={}ms,info={},keyId={},ip={}", (System.currentTimeMillis() - start), info, keyId, ip);
        return 200;
    }

    @Override
    public AddMethodLogVo addMethodLog(AddMethodLogVo addMethodLogVo) {
        log.info("addMethodLog start,reqVo={}", JsonUtil.objToStr(addMethodLogVo));
        long start = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("addMethodLog success,cost={}ms,reqVo={}", (System.currentTimeMillis() - start), JsonUtil.objToStr(addMethodLogVo));
        return addMethodLogVo;
    }

//    @PostMapping("/addBizLog")
//    public Integer addBizLog(String info, Integer keyId, String ip) {
////        Integer result = bizLogService.addBizLog(info, keyId, ip);
////        return result;
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(6000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("666");
//        return 666;
//    }
}
