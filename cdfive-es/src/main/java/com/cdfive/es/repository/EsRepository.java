package com.cdfive.es.repository;

import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.DeleteByQuery;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.query.UpdateByQuery;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.es.vo.EsValueCountVo;
import com.cdfive.es.vo.EsWriteOptionVo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author cdfive
 */
public interface EsRepository<ENTITY, ID> {

    void save(ENTITY entity);

    void save(ENTITY entity, EsWriteOptionVo esWriteOptionVo);

    void save(Collection<ENTITY> entities);

    void save(Collection<ENTITY> entities, EsWriteOptionVo esWriteOptionVo);

    void update(ID id, Map<String, Object> params);

    void update(ID id, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo);

    void update(Collection<ID> ids, List<Map<String, Object>> params);

    void update(Collection<ID> ids, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo);

    void update(Collection<ID> ids, Map<String, Object> params);

    void update(Collection<ID> ids, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo);

    void updateByQuery(UpdateByQuery updateByQuery);

    void updateByQueryAsync(UpdateByQuery updateByQuery);

    void updateByScript(ID id, String scriptCode, Map<String, Object> params);

    void updateByScript(ID id, String scriptCode, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo);

    void updateByScript(Collection<ID> ids, String scriptCode, List<Map<String, Object>> params);

    void updateByScript(Collection<ID> ids, String scriptCode, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo);

    void updateByScript(Collection<ID> ids, String scriptCode, Map<String, Object> params);

    void updateByScript(Collection<ID> ids, String scriptCode, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo);

    void updateByScriptId(ID id, String scriptId, Map<String, Object> params);

    void updateByScriptId(ID id, String scriptId, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo);

    void updateByScriptId(Collection<ID> ids, String scriptId, List<Map<String, Object>> params);

    void updateByScriptId(Collection<ID> ids, String scriptId, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo);

    void updateByScriptId(Collection<ID> ids, String scriptId, Map<String, Object> params);

    void updateByScriptId(Collection<ID> ids, String scriptId, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo);

    void saveOrUpdate(ENTITY entity);

    void saveOrUpdate(ENTITY entity, EsWriteOptionVo esWriteOptionVo);

    void saveOrUpdate(Collection<ENTITY> entities);

    void saveOrUpdate(Collection<ENTITY> entities, EsWriteOptionVo esWriteOptionVo);

    void delete(ID id);

    void delete(ID id, EsWriteOptionVo esWriteOptionVo);

    void delete(Collection<ID> ids);

    void delete(Collection<ID> ids, EsWriteOptionVo esWriteOptionVo);

    void deleteByQuery(DeleteByQuery deleteByQuery);

    void deleteByQueryAsync(DeleteByQuery deleteByQuery);

    void deleteAll();

    EsEntityVo<ENTITY> findOne(ID id);

    List<EsEntityVo<ENTITY>> findAll(Collection<ID> ids);

    Page<EsEntityVo<ENTITY>> search(SearchQuery searchQuery);

    SearchResponse search(SearchRequest searchRequest);

    Map<String, List<EsValueCountVo>> aggregate(AggregateQuery aggregateQuery);

    void refresh();

    List<String> analyze(String analyzer, String... text);

    List<String> analyzeWithField(String field, String... text);
}
