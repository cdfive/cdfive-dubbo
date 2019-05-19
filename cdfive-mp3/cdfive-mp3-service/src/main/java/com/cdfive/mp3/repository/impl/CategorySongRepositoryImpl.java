package com.cdfive.mp3.repository.impl;

import com.cdfive.mp3.repository.CategorySongRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author cdfive
 */
@Repository
public class CategorySongRepositoryImpl implements CategorySongRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
}
