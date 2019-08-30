package com.cdfive.log.repository;

import com.cdfive.log.po.BizLogPo;
import com.cdfive.support.jpa.repository.BaseRepository;

/**
 * @author cdfive

 */
public interface BizLogRepository extends BaseRepository<BizLogPo, Integer>, BizLogRepositoryCustom {
}
