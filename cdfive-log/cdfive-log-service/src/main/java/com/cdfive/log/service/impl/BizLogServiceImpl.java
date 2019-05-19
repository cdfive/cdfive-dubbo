package com.cdfive.log.service.impl;

import com.cdfive.log.po.BizLogPo;
import com.cdfive.log.repository.BizLogRepository;
import com.cdfive.log.service.BizLogService;
import com.cdfive.log.service.AbstractLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author cdfive
 */
@Service("bizLogService")
public class BizLogServiceImpl extends AbstractLogService implements BizLogService {

    @Autowired
    private BizLogRepository bizLogRepository;

    @Override
    public Integer addBizLog(String info, Integer keyId, String ip) {
        checkNotNull(info, "信息不能为空");
        checkNotNull(info, "keyId不能为空");
        checkNotNull(info, "ip不能为空");

        BizLogPo bizLogPo = new BizLogPo();
        bizLogPo.setInfo(info);
        bizLogPo.setKeyId(keyId);
        bizLogPo.setIp(ip);

        bizLogPo.setCreateTime(new Date());
        bizLogPo.setUpdateTime(new Date());
        bizLogPo.setDeleted(false);

        bizLogRepository.save(bizLogPo);
        return bizLogPo.getId();
    }
}
