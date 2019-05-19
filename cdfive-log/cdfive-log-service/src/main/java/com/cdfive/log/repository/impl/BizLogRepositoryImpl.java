package com.cdfive.log.repository.impl;

import com.cdfive.log.repository.BizLogRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author cdfive
 */
@Repository
public class BizLogRepositoryImpl implements BizLogRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
}
