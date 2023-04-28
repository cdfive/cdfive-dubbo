package com.cdfive.search.repository;

import com.cdfive.es.repository.AbstractEsRepository;
import com.cdfive.search.eo.LogRequestEo;
import org.springframework.stereotype.Repository;

/**
 * @author cdfive
 */
@Repository
public class LogRequestEsRepository extends AbstractEsRepository<LogRequestEo, String> {

}
