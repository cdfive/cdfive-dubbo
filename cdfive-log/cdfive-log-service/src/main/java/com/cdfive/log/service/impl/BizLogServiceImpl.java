package com.cdfive.log.service.impl;

import com.cdfive.log.exception.LogServiceException;
import com.cdfive.log.po.BizLogPo;
import com.cdfive.log.repository.BizLogRepository;
import com.cdfive.log.service.BizLogService;
import com.cdfive.log.service.AbstractLogService;
import com.cdfive.search.service.BizLogEsService;
import com.cdfive.search.vo.SaveBizLogReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@Slf4j
@Service("bizLogService")
public class BizLogServiceImpl extends AbstractLogService implements BizLogService {

    @Autowired
    private BizLogEsService bizLogEsService;

    @Autowired
    private BizLogRepository bizLogRepository;

    @Override
    public Integer addBizLog(String info, Integer keyId, String ip) {
        log.info("bizLogService=>addBizLog");
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

    @Override
    public void syncAllBizLogToEs() {
        Page<BizLogPo> page = null;
        for (Pageable pageable = PageRequest.of(0, 100);
             (page = bizLogRepository.findAll(pageable)).hasContent();
             pageable = pageable.next()) {

            List<BizLogPo> songPos = page.getContent();
            List<SaveBizLogReqVo> saveReqVos = songPos.stream().map(o -> {
                SaveBizLogReqVo saveReqVo = new SaveBizLogReqVo();
                BeanUtils.copyProperties(o, saveReqVo);
                return saveReqVo;
            }).collect(Collectors.toList());
            log.info("syncAllToEs save {}", songPos.size());
            bizLogEsService.saveBizLogs(saveReqVos);
        }
    }
}
