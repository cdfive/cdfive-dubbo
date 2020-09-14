package com.cdfive.ctf.es.repository;

import com.cdfive.ctf.es.query.DeleteQuery;
import com.cdfive.ctf.es.query.SearchQuery;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * @author cdfive
 */
public interface EsRepository<Entity, Id> {

    void save(Entity entity);

    void save(Collection<Entity> entities);

    void delete(Id id);

    void delete(Collection<Id> ids);

    void deleteByQuery(DeleteQuery deleteQuery);

    void deleteAll();

    Entity findOne(Id id);

    Page<Entity> search(SearchQuery searchQuery);
}
