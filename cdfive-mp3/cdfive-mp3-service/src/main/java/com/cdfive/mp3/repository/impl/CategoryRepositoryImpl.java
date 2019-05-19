package com.cdfive.mp3.repository.impl;

import com.cdfive.mp3.repository.CategoryRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author cdfive
 */
@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
}
