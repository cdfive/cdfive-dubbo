package com.cdfive.log.repository;

import com.cdfive.log.po.BizLogPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cdfive

 */
public interface BizLogRepository extends JpaRepository<BizLogPo, Integer>, BizLogRepositoryCustom {
}
