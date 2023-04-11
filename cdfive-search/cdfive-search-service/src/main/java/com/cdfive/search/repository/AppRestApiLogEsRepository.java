package com.cdfive.search.repository;

import com.cdfive.es.repository.AbstractEsRepository;
import com.cdfive.search.eo.AppRestApiLogEo;
import org.springframework.stereotype.Repository;

/**
 * @author xiejihan
 * @date 2023-04-07
 */
@Repository
public class AppRestApiLogEsRepository extends AbstractEsRepository<AppRestApiLogEo, String> {

}
