package com.cdfive.log.listener;

import com.cdfive.log.event.AddBizLogEvent;
import com.cdfive.log.po.BizLogPo;
import com.cdfive.search.service.BizLogEsService;
import com.cdfive.search.vo.bizlog.SaveBizLogReqVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author cdfive
 */
@Slf4j
@Component
public class AddBizLogEventListener {

    @Autowired
    private BizLogEsService bizLogEsService;

//    @TransactionalEventListener
    @EventListener
    public void handleAddBizLogEvent(AddBizLogEvent event) {
        log.info("AddBizLogEventListener add biz log");
        BizLogPo po = event.getBizLogPo();
        SaveBizLogReqVo saveReqVo = new SaveBizLogReqVo();
        BeanUtils.copyProperties(po, saveReqVo);
        saveReqVo.setId(po.getId());
        bizLogEsService.saveBizLogs(Lists.newArrayList(saveReqVo));
    }
}
