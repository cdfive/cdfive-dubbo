package com.cdfive.search.repository;

import com.cdfive.search.eo.BizLogEo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author cdfive
 */
public interface BizLogEsRepository extends ElasticsearchRepository<BizLogEo, Integer> {


}
