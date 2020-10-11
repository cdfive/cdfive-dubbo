package com.cdfive.ctf.es.repository;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.util.GenericClassUtil;
import com.cdfive.ctf.es.annotation.Document;
import com.cdfive.ctf.es.config.EsProperties;
import com.cdfive.ctf.es.query.DeleteQuery;
import com.cdfive.ctf.es.query.SearchQuery;
import com.cdfive.ctf.es.query.UpdateQuery;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author cdfive
 */
public abstract class AbstractEsRepository<Entity, Id> implements EsRepository<Entity, Id>, InitializingBean {

    @Autowired
    protected RestHighLevelClient client;
    @Autowired
    private EsProperties esProperties;

    protected String index;
    protected Class<Entity> entityClass;
    protected Field idField;

    @Override
    public void save(Entity entity) {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(this.getId(entity).toString());
        indexRequest.source(JSON.toJSONString(entity), XContentType.JSON);
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es save entity error", e);
        }
    }

    @Override
    public void save(Collection<Entity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            throw new RuntimeException("es save entities but empty entites");
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (Entity entity : entities) {
            bulkRequest.add(new IndexRequest(index).id(this.getId(entity).toString()).source(JSON.toJSONString(entity), XContentType.JSON));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es save entities error", e);
        }
    }

    @Override
    public void update(Id id, Map<String, Object> params) {
        if (id == null) {
            throw new RuntimeException("es update entity but missing id");
        }

        if (params == null || params.size() == 0) {
            throw new RuntimeException("es update but empty params");
        }

        UpdateRequest updateRequest = new UpdateRequest(index, id.toString());
        updateRequest.doc(JSON.toJSONString(params), XContentType.JSON);
        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es update error", e);
        }
    }

    @Override
    public void update(Collection<Id> ids, List<Map<String, Object>> params) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es update batch but empty ids");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("es update batch but empty params");
        }

        if (ids.size() != params.size()) {
            throw new RuntimeException("es update batch but size of ids and params not equal");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<Id> idsIterator = ids.iterator();
        Iterator<Map<String, Object>> mapsIterator = params.iterator();
        for (int i = 0; i < size; i++) {
            bulkRequest.add(new UpdateRequest(index, idsIterator.next().toString()).doc(JSON.toJSONString(mapsIterator.next()), XContentType.JSON));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es update batch error", e);
        }
    }

    @Override
    public void update(Collection<Id> ids, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es update batch but empty ids");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("es update batch but empty params");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<Id> idsIterator = ids.iterator();
        for (int i = 0; i < size; i++) {
            bulkRequest.add(new UpdateRequest(index, idsIterator.next().toString()).doc(JSON.toJSONString(params), XContentType.JSON));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es update batch error", e);
        }
    }

    @Override
    public void updateByQuery(UpdateQuery updateQuery) {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
        QueryBuilder query = updateQuery.getQuery();
        updateByQueryRequest.setQuery(query);

        Integer batchSize = updateQuery.getBatchSize();
        if (batchSize != null) {
            updateByQueryRequest.setBatchSize(batchSize);
        }

        try {
            client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es updateByQuery error", e);
        }
    }

    @Override
    public void updateByScript(Id id, String script, Map<String, Object> params) {
        if (id == null) {
            throw new RuntimeException("es updateByScript but missing id");
        }

        if (StringUtils.isEmpty(script)) {
            throw new RuntimeException("es updateByScript but empty script");
        }

        UpdateRequest updateRequest = new UpdateRequest(index, id.toString());
        Script inline = new Script(ScriptType.INLINE, "painless", script, params);
        updateRequest.script(inline);
        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es updateByScript error", e);
        }
    }

    @Override
    public void updateByScript(Collection<Id> ids, String script, List<Map<String, Object>> params) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es updateByScript batch but empty ids");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("es updateByScript batch but empty params");
        }

        if (ids.size() != params.size()) {
            throw new RuntimeException("es updateByScript batch but size of ids and params not equal");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<Id> idsIterator = ids.iterator();
        Iterator<Map<String, Object>> paramsIterator = params.iterator();
        for (int i = 0; i < size; i++) {
            bulkRequest.add(new UpdateRequest(index, idsIterator.next().toString()).script(new Script(ScriptType.INLINE, "painless", script, paramsIterator.next())));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es updateByScript batch error", e);
        }
    }

    @Override
    public void updateByScript(Collection<Id> ids, String script, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es updateByScript batch but empty ids");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("es updateByScript batch but empty params");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<Id> idsIterator = ids.iterator();
        for (int i = 0; i < size; i++) {
            bulkRequest.add(new UpdateRequest(index, idsIterator.next().toString()).script(new Script(ScriptType.INLINE, "painless", script, params)));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es updateByScript batch error", e);
        }
    }

    @Override
    public void updateByScriptId(Id id, String scriptId, Map<String, Object> params) {
        if (id == null) {
            throw new RuntimeException("es updateByScriptId but missing id");
        }

        if (StringUtils.isEmpty(scriptId)) {
            throw new RuntimeException("es updateByScriptId but empty scriptId");
        }

        UpdateRequest updateRequest = new UpdateRequest(index, id.toString());
        Script inline = new Script(ScriptType.STORED, null, scriptId, params);
        updateRequest.script(inline);
        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es updateByScriptId error", e);
        }
    }

    @Override
    public void updateByScriptId(Collection<Id> ids, String scriptId, List<Map<String, Object>> params) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es updateByScriptId batch but empty ids");
        }

        if (StringUtils.isEmpty(scriptId)) {
            throw new RuntimeException("es updateByScriptId batch but empty scriptId");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("es updateByScriptId batch but empty params");
        }

        if (ids.size() != params.size()) {
            throw new RuntimeException("es updateByScriptId batch but size of ids and params not equal");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<Id> idsIterator = ids.iterator();
        Iterator<Map<String, Object>> paramsIterator = params.iterator();
        for (int i = 0; i < size; i++) {
            bulkRequest.add(new UpdateRequest(index, idsIterator.next().toString()).script(new Script(ScriptType.STORED, null, scriptId, paramsIterator.next())));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es updateByScriptId batch error", e);
        }
    }

    @Override
    public void updateByScriptId(Collection<Id> ids, String scriptId, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es updateByScriptId batch but empty ids");
        }

        if (StringUtils.isEmpty(scriptId)) {
            throw new RuntimeException("es updateByScriptId batch but empty scriptId");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("es updateByScriptId batch but empty params");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<Id> idsIterator = ids.iterator();
        for (int i = 0; i < size; i++) {
            bulkRequest.add(new UpdateRequest(index, idsIterator.next().toString()).script(new Script(ScriptType.STORED, null, scriptId, params)));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es updateByScriptId batch error", e);
        }
    }

    @Override
    public void saveOrUpdate(Entity entity) {
        UpdateRequest updateRequest = new UpdateRequest(index, this.getId(entity).toString());
        updateRequest.doc(JSON.toJSONString(entity), XContentType.JSON);
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(this.getId(entity).toString());
        indexRequest.source(JSON.toJSONString(entity), XContentType.JSON);
        updateRequest.upsert(indexRequest);
        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es saveOrUpdate entity error", e);
        }
    }

    @Override
    public void saveOrUpdate(Collection<Entity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            throw new RuntimeException("es saveOrUpdate entities but empty entites");
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (Entity entity : entities) {
            bulkRequest.add(new UpdateRequest(index, this.getId(entity).toString()).doc(JSON.toJSONString(entity), XContentType.JSON)
                    .upsert(new IndexRequest(index).id(this.getId(entity).toString()).source(JSON.toJSONString(entity), XContentType.JSON)));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es saveOrUpdate entities error", e);
        }
    }

    @Override
    public void delete(Id id) {
        if (id == null) {
            throw new RuntimeException("es delete entity but missing id");
        }

        DeleteRequest deleteRequest = new DeleteRequest(index, id.toString());
        try {
            client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es delete entity error", e);
        }
    }

    @Override
    public void delete(Collection<Id> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es delete entities but empty ids");
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (Id id : ids) {
            bulkRequest.add(new DeleteRequest(index, id.toString()));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es delete entities error", e);
        }
    }

    @Override
    public void deleteByQuery(DeleteQuery deleteQuery) {
        QueryBuilder query = deleteQuery.getQuery();

        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(index);
        deleteByQueryRequest.setQuery(query);

        Integer batchSize = deleteQuery.getBatchSize();
        if (batchSize != null) {
            deleteByQueryRequest.setBatchSize(batchSize);
        }

        try {
            client.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es deleteByQuery error", e);
        }
    }

    @Override
    public void deleteAll() {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.withQuery(QueryBuilders.matchAllQuery());
        this.deleteByQuery(deleteQuery);
    }

    @Override
    public Entity findOne(Id id) {
        if (id == null) {
            throw new RuntimeException("es find one entity but missing id");
        }

        GetRequest getRequest = new GetRequest(index, id.toString());
        GetResponse getResponse;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es find one entity error", e);
        }

        if (!getResponse.isExists()) {
            return null;
        }

        String source = getResponse.getSourceAsString();
        return JSON.parseObject(source, entityClass);
    }

    @Override
    public Page<Entity> search(SearchQuery searchQuery) {
        QueryBuilder queryBuilder = searchQuery.getQuery();
        Pageable pageable = searchQuery.getPageable();
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        List<SortBuilder> sorts = searchQuery.getSorts();
        if (!CollectionUtils.isEmpty(sorts)) {
            for (SortBuilder sort : sorts) {
                searchSourceBuilder.sort(sort);
            }
        }

        if (pageable.isPaged()) {
            if (esProperties.getTrackTotalHits() != null && esProperties.getTrackTotalHits()) {
                searchSourceBuilder.trackTotalHits(true);
            }
            searchSourceBuilder.from((int) pageable.getOffset());
            searchSourceBuilder.size(pageable.getPageSize());
        }

        List<String> fields = searchQuery.getFields();
        if (!CollectionUtils.isEmpty(fields)) {
            FetchSourceContext sourceContext = new FetchSourceContext(true, fields.toArray(new String[]{}), null);
            searchSourceBuilder.fetchSource(sourceContext);
        }

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es search error", e);
        }

        if (!RestStatus.OK.equals(searchResponse.status())) {
            throw new RuntimeException("es search fail,status=" + searchResponse.status());
        }

        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        if (total == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<Entity> entities = new ArrayList<>();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            Entity entity = JSON.parseObject(source, entityClass);
            entities.add(entity);
        }

        Page page = new PageImpl(entities, pageable, total);
        return page;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        entityClass = GenericClassUtil.getGenericTypeFromSuperClass(this.getClass(), 0);
        Document document = AnnotationUtils.findAnnotation(entityClass, Document.class);
        if (document == null) {
            throw new RuntimeException(entityClass.getName() + " must be with annotation:'cn.wine.ms.search.elasticsearch.annotation.Document'");
        }
        index = document.index();

        for (Field field : entityClass.getDeclaredFields()) {
            com.cdfive.ctf.es.annotation.Id id = field.getAnnotation(com.cdfive.ctf.es.annotation.Id.class);
            if (id != null) {
                if (idField != null) {
                    throw new RuntimeException("Duplicate @cn.wine.ms.search.support.annotation.Id field in " + entityClass.getName());
                }
                idField = field;
                idField.setAccessible(true);
            }
        }
        if (idField == null) {
            throw new RuntimeException("Missing @cn.wine.ms.search.support.annotation.Id field in " + entityClass.getName());
        }
    }

    protected Id getId(Entity entity) {
        try {
            return (Id) idField.get(entity);
        } catch (Exception e) {
            throw new RuntimeException("getId error in " + entityClass.getName(), e);
        }
    }
}
