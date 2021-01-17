package com.cdfive.search.repository;

import com.cdfive.es.repository.AbstractEsRepository;
import com.cdfive.search.eo.BizLogEo;
import org.springframework.stereotype.Repository;

/**
 * @author cdfive
 */
@Repository
public class BizLogEsRepository extends AbstractEsRepository<BizLogEo, Integer> {

}
