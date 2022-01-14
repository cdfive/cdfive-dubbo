package com.cdfive.es.repository;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.util.GenericClassUtil;
import com.cdfive.es.annotation.Document;
import com.cdfive.es.annotation.Id;
import com.cdfive.es.constant.EsConstant;
import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.DeleteByQuery;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.query.UpdateByQuery;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.es.vo.EsValueCountVo;
import com.cdfive.es.vo.EsWriteOptionVo;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.UnmappedTerms;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiejihan
 * @date 2022-01-05
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractEsRepository<ENTITY, ID> implements EsRepository<ENTITY, ID>, InitializingBean {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestHighLevelClient client;

    protected Class<ENTITY> entityClass;
    protected String index;
    protected Field idField;

    @Override
    public void save(ENTITY entity) {
        this.save(entity, null);
    }

    @Override
    public void save(ENTITY entity, EsWriteOptionVo esWriteOptionVo) {
        if (entity == null) {
            return;
        }

        ID id = this.getId(entity);
        if (id == null) {
            throw new RuntimeException("id can't be null");
        }

        IndexRequest indexRequest = new IndexRequest(this.index);
        indexRequest.id(id.toString());
        indexRequest.source(JSON.toJSONString(entity), XContentType.JSON);

        if (esWriteOptionVo != null) {
            indexRequest.setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                    .routing(esWriteOptionVo.getRouting())
                    .version(esWriteOptionVo.getVersion())
                    .versionType(esWriteOptionVo.getVersionType());
        }

        try {
            this.client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es save entity error", e);
        }
    }

    @Override
    public void save(Collection<ENTITY> entities) {
        this.save(entities, null);
    }

    @Override
    public void save(Collection<ENTITY> entities, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (ENTITY entity : entities) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new IndexRequest(this.index).id(this.getId(entity).toString()).source(JSON.toJSONString(entity), XContentType.JSON));
            } else {
                bulkRequest.add(new IndexRequest(this.index).id(this.getId(entity).toString()).source(JSON.toJSONString(entity), XContentType.JSON)
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));
            }
        }
        try {
            this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es save entities error", e);
        }
    }

    @Override
    public void update(ID id, Map<String, Object> params) {
        this.update(id, params, null);
    }

    @Override
    public void update(ID id, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        if (id == null) {
            throw new RuntimeException("es update entity but id is null");
        }

        if (ObjectUtils.isEmpty(params)) {
            return;
        }

        UpdateRequest updateRequest = new UpdateRequest(this.index, id.toString());
        updateRequest.doc(JSON.toJSONString(params), XContentType.JSON);

        if (esWriteOptionVo != null) {
            updateRequest.setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                    .routing(esWriteOptionVo.getRouting())
                    .version(esWriteOptionVo.getVersion())
                    .versionType(esWriteOptionVo.getVersionType());
        }

        try {
            this.client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es update error", e);
        }
    }

    @Override
    public void update(Collection<ID> ids, List<Map<String, Object>> params) {
        this.update(ids, params, null);
    }

    @Override
    public void update(Collection<ID> ids, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es update batch but empty ids");
        }

        if (CollectionUtils.isEmpty(params)) {
            return;
        }

        if (ids.size() != params.size()) {
            throw new RuntimeException("es update batch but size of ids and params not equal");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<ID> idsIterator = ids.iterator();
        Iterator<Map<String, Object>> paramsIterator = params.iterator();
        for (int i = 0; i < size; i++) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).doc(JSON.toJSONString(paramsIterator.next()), XContentType.JSON));
            } else {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).doc(JSON.toJSONString(paramsIterator.next()), XContentType.JSON)
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));

            }
        }
        try {
            this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es update batch error", e);
        }
    }

    @Override
    public void update(Collection<ID> ids, Map<String, Object> params) {
        this.update(ids, params, null);
    }

    @Override
    public void update(Collection<ID> ids, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es update batch but empty ids");
        }

        if (ObjectUtils.isEmpty(params)) {
            throw new RuntimeException("es update batch but empty params");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<ID> idsIterator = ids.iterator();
        for (int i = 0; i < size; i++) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).doc(JSON.toJSONString(params), XContentType.JSON));
            } else {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).doc(JSON.toJSONString(params), XContentType.JSON)
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));
            }
        }
        try {
            this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es update batch error", e);
        }
    }

    @Override
    public void updateByQuery(UpdateByQuery updateByQuery) {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(this.index);
        QueryBuilder query = updateByQuery.getQuery();
        updateByQueryRequest.setQuery(query);

        Integer batchSize = updateByQuery.getBatchSize();
        if (batchSize != null) {
            updateByQueryRequest.setBatchSize(batchSize);
        }

        if (!StringUtils.isEmpty(updateByQuery.getScript())) {
            Script inline = new Script(ScriptType.INLINE, EsConstant.LANG_PAINLESS, updateByQuery.getScript(), updateByQuery.getParams());
            updateByQueryRequest.setScript(inline);
        }

        try {
            client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es updateByQuery error", e);
        }
    }

    @Override
    public void updateByScript(ID id, String scriptCode, Map<String, Object> params) {
        this.updateByScript(id, ScriptType.INLINE, scriptCode, params, null);
    }

    @Override
    public void updateByScript(ID id, String scriptCode, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        this.updateByScript(id, ScriptType.INLINE, scriptCode, params, esWriteOptionVo);
    }

    @Override
    public void updateByScript(Collection<ID> ids, String scriptCode, List<Map<String, Object>> params) {
        this.updateByScript(ids, ScriptType.INLINE, scriptCode, params, null);
    }

    @Override
    public void updateByScript(Collection<ID> ids, String scriptCode, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo) {
        this.updateByScript(ids, ScriptType.INLINE, scriptCode, params, esWriteOptionVo);
    }

    @Override
    public void updateByScript(Collection<ID> ids, String scriptCode, Map<String, Object> params) {
        this.updateByScript(ids, ScriptType.INLINE, scriptCode, params, null);
    }

    @Override
    public void updateByScript(Collection<ID> ids, String scriptCode, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        this.updateByScript(ids, ScriptType.INLINE, scriptCode, params, esWriteOptionVo);
    }

    @Override
    public void updateByScriptId(ID id, String scriptId, Map<String, Object> params) {
        this.updateByScript(id, ScriptType.STORED, scriptId, params, null);
    }

    @Override
    public void updateByScriptId(ID id, String scriptId, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        this.updateByScript(id, ScriptType.STORED, scriptId, params, esWriteOptionVo);
    }

    @Override
    public void updateByScriptId(Collection<ID> ids, String scriptId, List<Map<String, Object>> params) {
        this.updateByScript(ids, ScriptType.STORED, scriptId, params, null);
    }

    @Override
    public void updateByScriptId(Collection<ID> ids, String scriptId, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo) {
        this.updateByScript(ids, ScriptType.STORED, scriptId, params, esWriteOptionVo);
    }

    @Override
    public void updateByScriptId(Collection<ID> ids, String scriptId, Map<String, Object> params) {
        this.updateByScript(ids, ScriptType.STORED, scriptId, params, null);
    }

    @Override
    public void updateByScriptId(Collection<ID> ids, String scriptId, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        this.updateByScript(ids, ScriptType.STORED, scriptId, params, esWriteOptionVo);
    }

    @Override
    public void saveOrUpdate(ENTITY entity) {
        this.saveOrUpdate(entity, null);
    }

    @Override
    public void saveOrUpdate(ENTITY entity, EsWriteOptionVo esWriteOptionVo) {
        if (entity == null) {
            return;
        }

        ID id = this.getId(entity);
        if (id == null) {
            throw new RuntimeException("id can't be null");
        }

        UpdateRequest updateRequest = new UpdateRequest(this.index, this.getId(entity).toString());
        updateRequest.doc(JSON.toJSONString(entity), XContentType.JSON);
        IndexRequest indexRequest = new IndexRequest(this.index);
        indexRequest.id(id.toString());
        indexRequest.source(JSON.toJSONString(entity), XContentType.JSON);
        updateRequest.upsert(indexRequest);

        if (esWriteOptionVo != null) {
            updateRequest.setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                    .routing(esWriteOptionVo.getRouting())
                    .version(esWriteOptionVo.getVersion())
                    .versionType(esWriteOptionVo.getVersionType());
        }

        try {
            this.client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es saveOrUpdate entity error", e);
        }
    }

    @Override
    public void saveOrUpdate(Collection<ENTITY> entities) {
        this.saveOrUpdate(entities, null);
    }

    @Override
    public void saveOrUpdate(Collection<ENTITY> entities, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (ENTITY entity : entities) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new UpdateRequest(this.index, this.getId(entity).toString()).doc(JSON.toJSONString(entity), XContentType.JSON)
                        .upsert(new IndexRequest(this.index).id(this.getId(entity).toString()).source(JSON.toJSONString(entity), XContentType.JSON)));
            } else {
                bulkRequest.add(new UpdateRequest(this.index, this.getId(entity).toString()).doc(JSON.toJSONString(entity), XContentType.JSON)
                        .upsert(new IndexRequest(this.index).id(this.getId(entity).toString()).source(JSON.toJSONString(entity), XContentType.JSON))
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));
            }
        }
        try {
            this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es saveOrUpdate entities error", e);
        }
    }

    @Override
    public void delete(ID id) {
        this.delete(id, null);
    }

    @Override
    public void delete(ID id, EsWriteOptionVo esWriteOptionVo) {
        if (id == null) {
            throw new RuntimeException("es delete entity but id is null");
        }

        DeleteRequest deleteRequest = new DeleteRequest(this.index, id.toString());

        if (esWriteOptionVo != null) {
            deleteRequest.setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                    .routing(esWriteOptionVo.getRouting())
                    .version(esWriteOptionVo.getVersion())
                    .versionType(esWriteOptionVo.getVersionType());
        }

        try {
            this.client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es delete entity error", e);
        }
    }

    @Override
    public void delete(Collection<ID> ids) {
        this.delete(ids);
    }

    @Override
    public void delete(Collection<ID> ids, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (ID id : ids) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new DeleteRequest(this.index, id.toString()));
            } else {
                bulkRequest.add(new DeleteRequest(this.index, id.toString())
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));
            }
        }
        try {
            this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es delete entities error", e);
        }
    }

    @Override
    public void deleteByQuery(DeleteByQuery deleteByQuery) {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(this.index);
        QueryBuilder query = deleteByQuery.getQuery();
        deleteByQueryRequest.setQuery(query);

        Integer batchSize = deleteByQuery.getBatchSize();
        if (batchSize != null && batchSize > 0) {
            deleteByQueryRequest.setBatchSize(batchSize);
        }


        if (deleteByQuery.getConflictAbort() != null) {
            deleteByQueryRequest.setConflicts(deleteByQuery.getConflictAbort() ? EsConstant.CONFLICTS_ABORT : EsConstant.CONFLICTS_PROCEED);
        }
        try {
            this.client.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es deleteByQuery error", e);
        }
    }

    @Override
    public void deleteByQuerySync(DeleteByQuery deleteByQuery) {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(this.index);
        QueryBuilder query = deleteByQuery.getQuery();
        deleteByQueryRequest.setQuery(query);

        Integer batchSize = deleteByQuery.getBatchSize();
        if (batchSize != null && batchSize > 0) {
            deleteByQueryRequest.setBatchSize(batchSize);
        }

        if (deleteByQuery.getConflictAbort() != null) {
            deleteByQueryRequest.setConflicts(deleteByQuery.getConflictAbort() ? EsConstant.CONFLICTS_ABORT : EsConstant.CONFLICTS_PROCEED);
        }

        this.client.deleteByQueryAsync(deleteByQueryRequest, RequestOptions.DEFAULT, new ActionListener<BulkByScrollResponse>() {
            @Override
            public void onResponse(BulkByScrollResponse bulkByScrollResponse) {

            }

            @Override
            public void onFailure(Exception e) {
                log.error("deleteByQuerySync error", e);
            }
        });
    }

    @Override
    public void deleteAll() {
        DeleteByQuery deleteQuery = new DeleteByQuery();
        deleteQuery.withQuery(QueryBuilders.matchAllQuery());
        this.deleteByQuery(deleteQuery);
    }

    @Override
    public void refresh() {
        RefreshRequest refreshRequest = new RefreshRequest(this.index);
        try {
            this.client.indices().refresh(refreshRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es refresh error", e);
        }
    }

    @Override
    public EsEntityVo<ENTITY> findOne(ID id) {
        if (id == null) {
            return null;
        }

        GetRequest getRequest = new GetRequest(this.index, id.toString());

        GetResponse getResponse;
        try {
            getResponse = this.client.get(getRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es find one entity error", e);
        }

        if (!getResponse.isExists()) {
            return null;
        }

        String source = getResponse.getSourceAsString();
        ENTITY entity = JSON.parseObject(source, this.entityClass);

        EsEntityVo esEntityVo = new EsEntityVo(entity);
        esEntityVo.setVersion(getResponse.getVersion());
        return esEntityVo;
    }

    @Override
    public List<EsEntityVo<ENTITY>> findAll(Collection<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        MultiGetRequest multiGetRequest = new MultiGetRequest();

        for (ID id : ids) {
            multiGetRequest.add(new MultiGetRequest.Item(this.index, id.toString()));
        }

        MultiGetResponse multiGetResponse;
        try {
            multiGetResponse = this.client.mget(multiGetRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es findAll entities error", e);
        }

        List<EsEntityVo<ENTITY>> esEntityVos = new ArrayList<>(ids.size());
        MultiGetItemResponse[] responses = multiGetResponse.getResponses();
        for (MultiGetItemResponse response : responses) {
            if (!response.getResponse().isExists()) {
                continue;
            }

            String source = response.getResponse().getSourceAsString();
            ENTITY entity = JSON.parseObject(source, this.entityClass);

            EsEntityVo esEntityVo = new EsEntityVo(entity);
            esEntityVo.setVersion(response.getResponse().getVersion());
            esEntityVos.add(esEntityVo);
        }

        return esEntityVos;
    }

    @Override
    public Page<EsEntityVo<ENTITY>> search(SearchQuery searchQuery) {
        QueryBuilder queryBuilder = searchQuery.getQuery();
        Pageable pageable = searchQuery.getPageable();
        if (pageable == null) {
            pageable = Pageable.unpaged();
            searchQuery.setPageable(pageable);
        }

        SearchRequest searchRequest = new SearchRequest(this.index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        List<SortBuilder> sorts = searchQuery.getSorts();
        if (!CollectionUtils.isEmpty(sorts)) {
            for (SortBuilder sort : sorts) {
                searchSourceBuilder.sort(sort);
            }
        }

        if (pageable.isPaged()) {
            searchSourceBuilder.from((int) pageable.getOffset());
            searchSourceBuilder.size(pageable.getPageSize());
        }

        if (searchQuery.isTrackTotalHits()) {
            searchSourceBuilder.trackTotalHits(true);
        }

        if (searchQuery.isVersion()) {
            searchSourceBuilder.version(true);
        }

        List<String> fields = searchQuery.getFields();
        if (!CollectionUtils.isEmpty(fields)) {
            FetchSourceContext sourceContext = new FetchSourceContext(true, fields.toArray(new String[]{}), null);
            searchSourceBuilder.fetchSource(sourceContext);
        }

        if (searchQuery.getCollapse() != null) {
            searchSourceBuilder.collapse(searchQuery.getCollapse());
        }

        if (!CollectionUtils.isEmpty(searchQuery.getAggregations())) {
            List<AggregationBuilder> aggregations = searchQuery.getAggregations();
            for (AggregationBuilder aggregation : aggregations) {
                searchSourceBuilder.aggregation(aggregation);
            }
        }

        searchRequest.source(searchSourceBuilder);

        if (log.isDebugEnabled()) {
            log.debug("searchDsl=>{}", searchSourceBuilder.toString());
        }

        SearchResponse searchResponse;
        try {
            searchResponse = this.client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es search error", e);
        }

        if (!RestStatus.OK.equals(searchResponse.status())) {
            throw new RuntimeException("es search fail,status=" + searchResponse.status());
        }

        return this.buildSearchResult(searchResponse, searchQuery);
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) {
        try {
            return this.client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es search error");
        }
    }

    @Override
    public Map<String, List<EsValueCountVo>> aggregate(AggregateQuery aggregateQuery) {
        QueryBuilder queryBuilder = aggregateQuery.getQuery();

        SearchRequest searchRequest = new SearchRequest(this.index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        List<AggregationBuilder> aggregations = aggregateQuery.getAggregations();
        if (!CollectionUtils.isEmpty(aggregations)) {
            for (AggregationBuilder aggregation : aggregations) {
                searchSourceBuilder.aggregation(aggregation);
            }
        }

        searchSourceBuilder.size(0);

        searchRequest.source(searchSourceBuilder);

        if (log.isDebugEnabled()) {
            log.debug("searchDsl=>{}", searchSourceBuilder.toString());
        }

        SearchResponse searchResponse;
        try {
            searchResponse = this.client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es aggregate error", e);
        }

        if (!RestStatus.OK.equals(searchResponse.status())) {
            throw new RuntimeException("es aggregate fail,status=" + searchResponse.status());
        }


        return this.buildAggregateResult(searchResponse);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.entityClass = GenericClassUtil.getGenericTypeFromSuperClass(this.getClass(), 0);

        Document document = AnnotationUtils.findAnnotation(this.entityClass, Document.class);
        if (document == null) {
            throw new RuntimeException(this.entityClass.getName() + " must be with annotation:'@cn.wine.base.elasticsearch.annotation.Document'");
        }
        this.index = document.index();

        for (Field field : this.entityClass.getDeclaredFields()) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                if (this.idField != null) {
                    throw new RuntimeException("Duplicate @cn.wine.base.elasticsearch.annotation.Id field in " + this.entityClass.getName() + " class");
                }
                this.idField = field;
                this.idField.setAccessible(true);
            }
        }
        if (this.idField == null) {
            throw new RuntimeException("Missing @cn.wine.base.elasticsearch.annotation.Id field in " + this.entityClass.getName() + " class");
        }
    }

    protected void updateByScript(ID id, ScriptType scriptType, String scriptIdOrCode, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        if (id == null) {
            throw new RuntimeException("es updateByScript but id is null");
        }

        if (scriptType == null) {
            throw new RuntimeException("es updateByScript but scriptType is null");
        }

        if (StringUtils.isEmpty(scriptIdOrCode)) {
            throw new RuntimeException("es updateByScript but scriptIdOrCode is empty");
        }

        UpdateRequest updateRequest = new UpdateRequest(this.index, id.toString());
        Script script = new Script(scriptType, EsConstant.LANG_PAINLESS, scriptIdOrCode, params);
        updateRequest.script(script);

        if (esWriteOptionVo != null) {
            updateRequest.setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                    .routing(esWriteOptionVo.getRouting())
                    .version(esWriteOptionVo.getVersion())
                    .versionType(esWriteOptionVo.getVersionType());
        }

        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es updateByScript error", e);
        }
    }

    public void updateByScript(Collection<ID> ids, ScriptType scriptType, String scriptIdOrCode, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es updateByScript batch but ids is empty");
        }

        if (scriptType == null) {
            throw new RuntimeException("es updateByScript but scriptType is null");
        }

        if (StringUtils.isEmpty(scriptIdOrCode)) {
            throw new RuntimeException("es updateByScript but scriptIdOrCode is empty");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("es updateByScript batch but params is empty");
        }

        if (ids.size() != params.size()) {
            throw new RuntimeException("es updateByScript batch but size of ids and params not equal");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<ID> idsIterator = ids.iterator();
        Iterator<Map<String, Object>> paramsIterator = params.iterator();
        for (int i = 0; i < size; i++) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).script(new Script(scriptType, EsConstant.LANG_PAINLESS, scriptIdOrCode, paramsIterator.next())));
            } else {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).script(new Script(scriptType, EsConstant.LANG_PAINLESS, scriptIdOrCode, paramsIterator.next()))
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));
            }
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es updateByScript batch error", e);
        }
    }

    public void updateByScript(Collection<ID> ids, ScriptType scriptType, String scriptIdOrCode, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new RuntimeException("es updateByScript batch but ids is empty");
        }

        if (scriptType == null) {
            throw new RuntimeException("es updateByScript but scriptType is null");
        }

        if (StringUtils.isEmpty(scriptIdOrCode)) {
            throw new RuntimeException("es updateByScript but scriptIdOrCode is empty");
        }

        if (ObjectUtils.isEmpty(params)) {
            throw new RuntimeException("es updateByScript batch but params is empty");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<ID> idsIterator = ids.iterator();
        for (int i = 0; i < size; i++) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).script(new Script(scriptType, EsConstant.LANG_PAINLESS, scriptIdOrCode, params)));
            } else {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).script(new Script(scriptType, EsConstant.LANG_PAINLESS, scriptIdOrCode, params))
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));
            }
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException("es updateByScript batch error", e);
        }
    }

    protected Page<EsEntityVo<ENTITY>> buildSearchResult(SearchResponse searchResponse, SearchQuery searchQuery) {
        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        if (total == 0) {
            return new PageImpl<>(Collections.emptyList(), searchQuery.getPageable(), 0);
        }

        List<EsEntityVo<ENTITY>> esEntityVos = new ArrayList<>();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            ENTITY entity = JSON.parseObject(source, this.entityClass);

            EsEntityVo esEntityVo = new EsEntityVo(entity);

            if (searchQuery.isVersion()) {
                esEntityVo.setVersion(hit.getVersion());
            }

            if (searchQuery.isScore()) {
                esEntityVo.setScore(hit.getScore());
            }

            esEntityVos.add(esEntityVo);
        }

        if (searchResponse.getAggregations() != null) {
            List<Aggregation> aggs = searchResponse.getAggregations().asList();
            if (!CollectionUtils.isEmpty(aggs)) {
                Aggregation agg = aggs.get(0);
                if (agg instanceof ParsedCardinality) {
                    total = ((ParsedCardinality) agg).getValue();
                }
            }
        }

        return new PageImpl(esEntityVos, searchQuery.getPageable(), total);
    }

    protected Map<String, List<EsValueCountVo>> buildAggregateResult(SearchResponse searchResponse) {
        Map<String, List<EsValueCountVo>> resultMap = new LinkedHashMap<>();
        this.buildAggregateResult(searchResponse.getAggregations(), resultMap);
        return resultMap;
    }

    protected void buildAggregateResult(Aggregations aggregations, Map<String, List<EsValueCountVo>> resultMap) {
        if (aggregations == null) {
            return;
        }

        Map<String, Aggregation> aggregationMap = aggregations.asMap();
        if (ObjectUtils.isEmpty(aggregationMap)) {
            return;
        }

        for (String key : aggregationMap.keySet()) {
            Aggregation aggregation = aggregationMap.get(key);
            if (aggregation == null) {
                continue;
            }
            if (aggregation instanceof UnmappedTerms) {
                continue;
            }

            if (aggregation instanceof ParsedFilter) {
                ParsedFilter parsedFilter = (ParsedFilter) aggregation;
                Aggregations subAggregations = parsedFilter.getAggregations();
                this.buildAggregateResult(subAggregations, resultMap);
            } else if (aggregation instanceof ParsedTerms) {
                List<? extends Terms.Bucket> buckets = ((ParsedTerms) aggregation).getBuckets();
                if (CollectionUtils.isEmpty(buckets)) {
                    continue;
                }

                resultMap.put(key, buckets.stream()
                        .map(o -> {
                            Aggregations subAgg = o.getAggregations();
                            List<Aggregation> subAggList = subAgg.asList();
                            if (CollectionUtils.isEmpty(subAggList)) {
                                return new EsValueCountVo(o.getKeyAsString(), o.getDocCount());
                            } else {
                                List<String> subValues = new ArrayList<>();
                                Aggregation firstAgg = subAggList.get(0);
                                if (firstAgg instanceof TopHits) {
                                    TopHits parsedTopHits = (TopHits) firstAgg;
                                    for (SearchHit hit : parsedTopHits.getHits()) {
                                        subValues.add(hit.getSourceAsString());
                                    }
                                    return new EsValueCountVo(o.getKeyAsString(), o.getDocCount(), subValues);
                                } else {
                                    return new EsValueCountVo(o.getKeyAsString(), o.getDocCount());
                                }
                            }
                        })
                        .collect(Collectors.toList()));
            } else if (aggregation instanceof ParsedCardinality) {
                ParsedCardinality parsedCardinality = (ParsedCardinality) aggregation;
                List<EsValueCountVo> esValueCountVos = new ArrayList<>();
                esValueCountVos.add(new EsValueCountVo(parsedCardinality.getName(), parsedCardinality.getValue()));
                resultMap.put(key, esValueCountVos);
            }
        }
    }

    protected ID getId(ENTITY entity) {

        try {
            return (ID) this.idField.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("get id error", e);
        }
    }
}
