package com.cdfive.log.repository.impl;

import com.cdfive.log.repository.MethodLogRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author cdfive
 */
@Repository
public class MethodLogRepositoryImpl implements MethodLogRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
}
