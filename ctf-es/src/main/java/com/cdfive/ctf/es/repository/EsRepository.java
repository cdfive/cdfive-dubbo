package com.cdfive.ctf.es.repository;

import com.cdfive.ctf.es.query.DeleteQuery;
import com.cdfive.ctf.es.query.SearchQuery;
import com.cdfive.ctf.es.query.UpdateQuery;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author cdfive
 */
public interface EsRepository<Entity, Id> {

    void save(Entity entity);

    void save(Collection<Entity> entities);

    void update(Id id, Map<String, Object> map);

    void update(Collection<Id> ids, List<Map<String, Object>> maps);

    void updateByQuery(UpdateQuery updateQuery);

    void updateByScript(Id id, String script, Map<String, Object> params);

    void updateByScript(Collection<Id> ids, String script, List<Map<String, Object>> params);

    void saveOrUpdate(Entity entity);

    void saveOrUpdate(Collection<Entity> entities);

    void delete(Id id);

    void delete(Collection<Id> ids);

    void deleteByQuery(DeleteQuery deleteQuery);

    void deleteAll();

    Entity findOne(Id id);

    Page<Entity> search(SearchQuery searchQuery);
}
