package com.cdfive.log.repository;

import com.cdfive.log.po.MethodLogPo;
import com.cdfive.support.jpa.repository.BaseRepository;

/**
 * @author cdfive
 */
public interface MethodLogRepository extends BaseRepository<MethodLogPo, Integer>, MethodLogRepositoryCustom {

}
