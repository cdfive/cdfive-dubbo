package com.cdfive.support.jpa.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author cdfive
 */
@NoRepositoryBean
public class BaseRepositoryBean<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public BaseRepositoryBean(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public BaseRepositoryBean(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public void persist(T entity) {
        getEntityManager().persist(entity);
    }
}
