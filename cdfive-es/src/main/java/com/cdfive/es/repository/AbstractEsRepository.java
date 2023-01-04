package com.cdfive.es.repository;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.util.CommonUtil;
import com.cdfive.common.util.GenericClassUtil;
import com.cdfive.es.annotation.Document;
import com.cdfive.es.annotation.Id;
import com.cdfive.es.constant.EsConstant;
import com.cdfive.es.exception.EsException;
import com.cdfive.es.query.*;
import com.cdfive.es.vo.BatchUpdateRespVo;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.es.vo.EsValueCountVo;
import com.cdfive.es.vo.EsWriteOptionVo;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.tasks.TaskSubmissionResponse;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.UnmappedTerms;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.tasks.TaskId;
import org.elasticsearch.tasks.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractEsRepository<ENTITY, ID> implements EsRepository<ENTITY, ID>, InitializingBean {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Value("${spring.data.elasticsearch.searchDslKeyword:#{null}}")
    private String searchDslKeyword;

    @Value("${spring.data.elasticsearch.aggregateDslKeyword:#{null}}")
    private String aggregateDslKeyword;

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
            throw new EsException("id can't be null");
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
            throw new EsException("es save entity error", e);
        }
    }

    @Override
    public BatchUpdateRespVo<ENTITY> save(Collection<ENTITY> entities) {
        return this.save(entities, null);
    }

    @Override
    public BatchUpdateRespVo<ENTITY> save(Collection<ENTITY> entities, EsWriteOptionVo esWriteOptionVo) {
        BatchUpdateRespVo<ENTITY> respVo = new BatchUpdateRespVo<>(CommonUtil.getTraceId());
        if (CollectionUtils.isEmpty(entities)) {
            return respVo;
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
            BulkResponse bulkResponse = this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
            this.buildEntitiesBulkResponse(bulkResponse, respVo, entities, "save");
            return respVo;
        } catch (Exception e) {
            throw new EsException("es save entities error", e);
        }
    }

    @Override
    public void update(ID id, Map<String, Object> params) {
        this.update(id, params, null);
    }

    @Override
    public void update(ID id, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        if (id == null) {
            throw new EsException("es update entity but id is null");
        }

        if (ObjectUtils.isEmpty(params)) {
            return;
        }

        UpdateRequest updateRequest = new UpdateRequest(this.index, id.toString());
        updateRequest.doc(JSON.toJSONString(params), XContentType.JSON);

        if (esWriteOptionVo != null) {
            updateRequest.setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                    .routing(esWriteOptionVo.getRouting());
        }

        try {
            this.client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es update error", e);
        }
    }

    @Override
    public BatchUpdateRespVo<ID> update(Collection<ID> ids, List<Map<String, Object>> params) {
        return this.update(ids, params, null);
    }

    @Override
    public BatchUpdateRespVo<ID> update(Collection<ID> ids, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo) {
        BatchUpdateRespVo<ID> respVo = new BatchUpdateRespVo<>(CommonUtil.getTraceId());
        if (CollectionUtils.isEmpty(ids)) {
            throw new EsException("es update batch but empty ids");
        }

        if (CollectionUtils.isEmpty(params)) {
            return respVo;
        }

        if (ids.size() != params.size()) {
            throw new EsException("es update batch but size of ids and params not equal");
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
                        .routing(esWriteOptionVo.getRouting()));

            }
        }
        try {
            BulkResponse bulkResponse = this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
            this.buildIdsBulkResponse(bulkResponse, respVo, ids, "update");
            return respVo;
        } catch (Exception e) {
            throw new EsException("es update batch error", e);
        }
    }

    @Override
    public BatchUpdateRespVo<ID> update(Collection<ID> ids, Map<String, Object> params) {
        return this.update(ids, params, null);
    }

    @Override
    public BatchUpdateRespVo<ID> update(Collection<ID> ids, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        BatchUpdateRespVo<ID> respVo = new BatchUpdateRespVo<>(CommonUtil.getTraceId());
        if (CollectionUtils.isEmpty(ids)) {
            throw new EsException("es update batch but empty ids");
        }

        if (ObjectUtils.isEmpty(params)) {
            throw new EsException("es update batch but empty params");
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
                        .routing(esWriteOptionVo.getRouting()));
            }
        }
        try {
            BulkResponse bulkResponse = this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
            this.buildIdsBulkResponse(bulkResponse, respVo, ids, "update");
            return respVo;
        } catch (Exception e) {
            throw new EsException("es update batch error", e);
        }
    }

    @Override
    public void updateByQuery(UpdateByQuery updateByQuery) {
        if (!StringUtils.hasText(updateByQuery.getScript()) && !StringUtils.hasText(updateByQuery.getScriptId())) {
            return;
        }

        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(this.index);
        QueryBuilder query = updateByQuery.getQuery();
        updateByQueryRequest.setQuery(query);

        if (!!StringUtils.hasText(updateByQuery.getScript())) {
            Script inline = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, updateByQuery.getScript(), updateByQuery.getParams());
            updateByQueryRequest.setScript(inline);
        } else if (!!StringUtils.hasText(updateByQuery.getScriptId())) {
            Script scriptStored = new Script(ScriptType.STORED, null, updateByQuery.getScriptId(), updateByQuery.getParams());
            updateByQueryRequest.setScript(scriptStored);
        }

        Integer batchSize = updateByQuery.getBatchSize();
        if (batchSize != null) {
            updateByQueryRequest.setBatchSize(batchSize);
        }

        Boolean conflictAbort = updateByQuery.getConflictAbort();
        if (conflictAbort != null) {
            updateByQueryRequest.setConflicts(conflictAbort ? EsConstant.CONFLICTS_ABORT : EsConstant.CONFLICTS_PROCEED);
        }

        Boolean refresh = updateByQuery.getRefresh();
        if (refresh != null) {
            updateByQueryRequest.setRefresh(refresh);
        }

        Boolean async = updateByQuery.getAsync();
        if (async != null && async) {
            this.client.updateByQueryAsync(updateByQueryRequest, RequestOptions.DEFAULT, new ActionListener<BulkByScrollResponse>() {
                @Override
                public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                    Runnable callback = updateByQuery.getCallback();
                    if (callback != null) {
                        callback.run();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("es updateByQueryAsync error", e);
                }
            });
        } else {
            try {
                BulkByScrollResponse bulkByScrollResponse = this.client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
                List<BulkItemResponse.Failure> bulkFailures = bulkByScrollResponse.getBulkFailures();
                if (!CollectionUtils.isEmpty(bulkFailures)) {
                    throw new EsException("es updateByQuery error,error size=" + bulkFailures.size());
                }
            } catch (Exception e) {
                throw new EsException("es updateByQuery error", e);
            }
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
            throw new EsException("id can't be null");
        }

        UpdateRequest updateRequest = new UpdateRequest(this.index, this.getId(entity).toString());
        updateRequest.doc(JSON.toJSONString(entity), XContentType.JSON);
        IndexRequest indexRequest = new IndexRequest(this.index);
        indexRequest.id(id.toString());
        indexRequest.source(JSON.toJSONString(entity), XContentType.JSON);
        updateRequest.upsert(indexRequest);

        if (esWriteOptionVo != null) {
            updateRequest.setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                    .routing(esWriteOptionVo.getRouting());
        }

        try {
            this.client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es saveOrUpdate entity error", e);
        }
    }

    @Override
    public BatchUpdateRespVo<ENTITY> saveOrUpdate(Collection<ENTITY> entities) {
        return this.saveOrUpdate(entities, null);
    }

    @Override
    public BatchUpdateRespVo<ENTITY> saveOrUpdate(Collection<ENTITY> entities, EsWriteOptionVo esWriteOptionVo) {
        BatchUpdateRespVo<ENTITY> respVo = new BatchUpdateRespVo<>(CommonUtil.getTraceId());
        if (CollectionUtils.isEmpty(entities)) {
            return respVo;
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (ENTITY entity : entities) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new UpdateRequest(this.index, this.getId(entity).toString()).doc(JSON.toJSONString(entity), XContentType.JSON)
                        .upsert(new IndexRequest(this.index).id(this.getId(entity).toString()).source(JSON.toJSONString(entity), XContentType.JSON)));
            } else {
                bulkRequest.add(new UpdateRequest(this.index, this.getId(entity).toString()).doc(JSON.toJSONString(entity), XContentType.JSON)
                        .upsert(new IndexRequest(this.index).id(this.getId(entity).toString()).source(JSON.toJSONString(entity), XContentType.JSON).version(esWriteOptionVo.getVersion()).versionType(esWriteOptionVo.getVersionType()))
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                );
            }
        }
        try {
            BulkResponse bulkResponse = this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
            this.buildEntitiesBulkResponse(bulkResponse, respVo, entities, "saveOrUpdate");
            return respVo;
        } catch (Exception e) {
            throw new EsException("es saveOrUpdate entities error", e);
        }
    }

    @Override
    public void delete(ID id) {
        this.delete(id, null);
    }

    @Override
    public void delete(ID id, EsWriteOptionVo esWriteOptionVo) {
        if (id == null) {
            throw new EsException("es delete entity but id is null");
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
            throw new EsException("es delete entity error", e);
        }
    }

    @Override
    public BatchUpdateRespVo<ID> delete(Collection<ID> ids) {
        return this.delete(ids, null);
    }

    @Override
    public BatchUpdateRespVo<ID> delete(Collection<ID> ids, EsWriteOptionVo esWriteOptionVo) {
        BatchUpdateRespVo<ID> respVo = new BatchUpdateRespVo<>(CommonUtil.getTraceId());
        if (CollectionUtils.isEmpty(ids)) {
            return respVo;
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
            BulkResponse bulkResponse = this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
            this.buildIdsBulkResponse(bulkResponse, respVo, ids, "delete");
            return respVo;
        } catch (Exception e) {
            throw new EsException("es delete entities error", e);
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

        Boolean conflictAbort = deleteByQuery.getConflictAbort();
        if (conflictAbort != null) {
            deleteByQueryRequest.setConflicts(conflictAbort ? EsConstant.CONFLICTS_ABORT : EsConstant.CONFLICTS_PROCEED);
        }

        Boolean refresh = deleteByQuery.getRefresh();
        if (refresh != null) {
            deleteByQueryRequest.setRefresh(refresh);
        }

        Boolean async = deleteByQuery.getAsync();
        if (async != null && async) {
            this.client.deleteByQueryAsync(deleteByQueryRequest, RequestOptions.DEFAULT, new ActionListener<BulkByScrollResponse>() {
                @Override
                public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                    Runnable callback = deleteByQuery.getCallback();
                    if (callback != null) {
                        callback.run();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("es deleteByQueryAsync error", e);
                }
            });
        } else {
            try {
                this.client.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
            } catch (Exception e) {
                throw new EsException("es deleteByQuery error", e);
            }
        }
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
            throw new EsException("es refresh error", e);
        }
    }

    @Override
    public void flush() {
        FlushRequest flushRequest = new FlushRequest(this.index);
        try {
            this.client.indices().flush(flushRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es flush error", e);
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
            throw new EsException("es find one entity error", e);
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
    public boolean exists(ID id) {
        if (id == null) {
            throw new EsException("es exists query but id is null");
        }

        GetRequest getRequest = new GetRequest(this.index, id.toString());
        getRequest.fetchSourceContext(new FetchSourceContext(false));

        try {
            return this.client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es exists query error", e);
        }
    }

    @Override
    public Map<ID, Boolean> exists(Collection<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }

        MultiGetRequest multiGetRequest = new MultiGetRequest();
        FetchSourceContext fetchSourceContext = new FetchSourceContext(false);
        for (ID id : ids) {
            multiGetRequest.add(new MultiGetRequest.Item(this.index, id.toString()).fetchSourceContext(fetchSourceContext));
        }

        MultiGetResponse multiGetResponse;
        try {
            multiGetResponse = this.client.mget(multiGetRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es exists multi query error", e);
        }

        MultiGetItemResponse[] responses = multiGetResponse.getResponses();
        Set<String> strIds = new HashSet<>(ids.size());
        for (MultiGetItemResponse response : responses) {
            if (!response.getResponse().isExists()) {
                continue;
            }
            strIds.add(response.getId());
        }

        return ids.stream().filter(Objects::nonNull).collect(Collectors.toMap(o -> o, o -> strIds.contains(String.valueOf(o)), (o, n) -> n));
    }

    @Override
    public long count(CountQuery countQuery) {
        QueryBuilder queryBuilder = countQuery.getQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        CountRequest countRequest = new CountRequest(this.index);
        countRequest.source(searchSourceBuilder);

        try {
            CountResponse countResponse = this.client.count(countRequest, RequestOptions.DEFAULT);
            return countResponse.getCount();
        } catch (Exception e) {
            throw new EsException("es count query error", e);
        }
    }

    @Override
    public List<EsEntityVo<ENTITY>> findAll(Collection<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        MultiGetRequest multiGetRequest = new MultiGetRequest();

        for (ID id : ids) {
            multiGetRequest.add(new MultiGetRequest.Item(this.index, id.toString()));
        }

        MultiGetResponse multiGetResponse;
        try {
            multiGetResponse = this.client.mget(multiGetRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es findAll entities error", e);
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
        SearchRequest searchRequest = new SearchRequest(this.index);
        SearchSourceBuilder searchSourceBuilder = searchQuery.toSearchSourceBuilder();

        if (log.isDebugEnabled()) {
            log.debug("searchDsl=>{}", searchSourceBuilder.toString());
        } else {
            if (searchDslKeyword != null && searchSourceBuilder.toString().contains(searchDslKeyword)) {
                log.info("searchDsl=>{}", searchSourceBuilder.toString());
            }
        }

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse;
        try {
            searchResponse = this.client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es search error", e);
        }

        if (!RestStatus.OK.equals(searchResponse.status())) {
            throw new EsException("es search fail,status=" + searchResponse.status());
        }

        return this.buildSearchResponse(searchResponse, searchQuery);
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) {
        try {
            return this.client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es search error");
        }
    }

    @Override
    public Map<String, List<EsValueCountVo>> aggregate(AggregateQuery aggregateQuery) {
        SearchRequest searchRequest = new SearchRequest(this.index);
        SearchSourceBuilder searchSourceBuilder = aggregateQuery.toSearchSourceBuilder();

        if (log.isDebugEnabled()) {
            log.debug("aggregateDsl=>{}", searchSourceBuilder.toString());
        } else {
            if (aggregateDslKeyword != null && searchSourceBuilder.toString().contains(aggregateDslKeyword)) {
                log.info("aggregateDsl=>{}", searchSourceBuilder.toString());
            }
        }

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse;
        try {
            searchResponse = this.client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es aggregate error", e);
        }

        if (!RestStatus.OK.equals(searchResponse.status())) {
            throw new EsException("es aggregate fail,status=" + searchResponse.status());
        }


        return this.buildAggregateResponse(searchResponse);
    }

    @Override
    public List<String> analyze(String analyzer, String... text) {
        AnalyzeRequest analyzeRequest = AnalyzeRequest.withIndexAnalyzer(this.index, analyzer, text);
        try {
            AnalyzeResponse analyzeResponse = this.client.indices().analyze(analyzeRequest, RequestOptions.DEFAULT);
            if (CollectionUtils.isEmpty(analyzeResponse.getTokens())) {
                return Collections.emptyList();
            }

            return analyzeResponse.getTokens().stream().map(o -> o.getTerm()).collect(Collectors.toList());
        } catch (Exception e) {
            throw new EsException("es analyze error", e);
        }
    }

    @Override
    public List<String> analyzeWithField(String field, String... text) {
        AnalyzeRequest analyzeRequest = AnalyzeRequest.withField(this.index, field, text);
        try {
            AnalyzeResponse analyzeResponse = this.client.indices().analyze(analyzeRequest, RequestOptions.DEFAULT);
            if (CollectionUtils.isEmpty(analyzeResponse.getTokens())) {
                return Collections.emptyList();
            }

            return analyzeResponse.getTokens().stream().map(o -> o.getTerm()).collect(Collectors.toList());
        } catch (Exception e) {
            throw new EsException("es analyzeWithField error", e);
        }
    }

    @Override
    public String reindex(ReindexRequest reindexRequest) {
        try {
            TaskSubmissionResponse taskSubmissionResponse = this.client.submitReindexTask(reindexRequest, RequestOptions.DEFAULT);
            return taskSubmissionResponse.getTask();
        } catch (Exception e) {
            throw new EsException("es reindex error", e);
        }
    }

    @Override
    public TaskInfo getTask(String taskId) {
        ListTasksRequest listTasksRequest = new ListTasksRequest();
        listTasksRequest.setTaskId(new TaskId(taskId));
        try {
            ListTasksResponse listTasksResponse = this.client.tasks().list(listTasksRequest, RequestOptions.DEFAULT);
            List<TaskInfo> tasks = listTasksResponse.getTasks();
            return (tasks != null && tasks.size() > 0) ? tasks.get(0) : null;
        } catch (Exception e) {
            throw new EsException("es get task error", e);
        }
    }

    @Override
    public ClusterHealthStatus getClusterHealthStatus() {
        ClusterHealthRequest clusterHealthRequest = new ClusterHealthRequest();
        try {
            ClusterHealthResponse clusterHealthResponse = this.client.cluster().health(clusterHealthRequest, RequestOptions.DEFAULT);
            return clusterHealthResponse.getStatus();
        } catch (Exception e) {
            throw new EsException("es get clusterHealthStatus error", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.entityClass = GenericClassUtil.getGenericTypeFromSuperClass(this.getClass(), 0);

        Document document = AnnotationUtils.findAnnotation(this.entityClass, Document.class);
        if (document == null) {
            throw new EsException(this.entityClass.getName() + " must be with annotation:'@com.cdfive.es.annotation.Document'");
        }
        this.index = document.index();

        for (Field field : this.entityClass.getDeclaredFields()) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                if (this.idField != null) {
                    throw new EsException("Duplicate @com.cdfive.es.annotation.Id field in " + this.entityClass.getName() + " class");
                }
                this.idField = field;
                this.idField.setAccessible(true);
            }
        }
        if (this.idField == null) {
            throw new EsException("Missing @com.cdfive.es.annotation.Id field in " + this.entityClass.getName() + " class");
        }
    }

    protected void updateByScript(ID id, ScriptType scriptType, String scriptIdOrCode, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        if (id == null) {
            throw new EsException("es updateByScript but id is null");
        }

        if (scriptType == null) {
            throw new EsException("es updateByScript but scriptType is null");
        }

        if (!StringUtils.hasText(scriptIdOrCode)) {
            throw new EsException("es updateByScript but scriptIdOrCode is empty");
        }

        UpdateRequest updateRequest = new UpdateRequest(this.index, id.toString());
        Script script = new Script(scriptType, ScriptType.INLINE.equals(scriptType) ? Script.DEFAULT_SCRIPT_LANG : null, scriptIdOrCode, params);
        updateRequest.script(script);

        if (esWriteOptionVo != null) {
            updateRequest.setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                    .routing(esWriteOptionVo.getRouting())
                    .version(esWriteOptionVo.getVersion())
                    .versionType(esWriteOptionVo.getVersionType());
        }

        try {
            this.client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es updateByScript error", e);
        }
    }

    protected void updateByScript(Collection<ID> ids, ScriptType scriptType, String scriptIdOrCode, List<Map<String, Object>> params, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new EsException("es updateByScript batch but ids is empty");
        }

        if (scriptType == null) {
            throw new EsException("es updateByScript but scriptType is null");
        }

        if (!StringUtils.hasText(scriptIdOrCode)) {
            throw new EsException("es updateByScript but scriptIdOrCode is empty");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new EsException("es updateByScript batch but params is empty");
        }

        if (ids.size() != params.size()) {
            throw new EsException("es updateByScript batch but size of ids and params not equal");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<ID> idsIterator = ids.iterator();
        Iterator<Map<String, Object>> paramsIterator = params.iterator();
        for (int i = 0; i < size; i++) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).script(new Script(scriptType, ScriptType.INLINE.equals(scriptType) ? Script.DEFAULT_SCRIPT_LANG : null, scriptIdOrCode, paramsIterator.next())));
            } else {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).script(new Script(scriptType, ScriptType.INLINE.equals(scriptType) ? Script.DEFAULT_SCRIPT_LANG : null, scriptIdOrCode, paramsIterator.next()))
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));
            }
        }
        try {
            this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es updateByScript batch error", e);
        }
    }

    protected void updateByScript(Collection<ID> ids, ScriptType scriptType, String scriptIdOrCode, Map<String, Object> params, EsWriteOptionVo esWriteOptionVo) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new EsException("es updateByScript batch but ids is empty");
        }

        if (scriptType == null) {
            throw new EsException("es updateByScript but scriptType is null");
        }

        if (!StringUtils.hasText(scriptIdOrCode)) {
            throw new EsException("es updateByScript but scriptIdOrCode is empty");
        }

        if (ObjectUtils.isEmpty(params)) {
            throw new EsException("es updateByScript batch but params is empty");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<ID> idsIterator = ids.iterator();
        for (int i = 0; i < size; i++) {
            if (esWriteOptionVo == null) {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).script(new Script(scriptType, ScriptType.INLINE.equals(scriptType) ? Script.DEFAULT_SCRIPT_LANG : null, scriptIdOrCode, params)));
            } else {
                bulkRequest.add(new UpdateRequest(this.index, idsIterator.next().toString()).script(new Script(scriptType, ScriptType.INLINE.equals(scriptType) ? Script.DEFAULT_SCRIPT_LANG : null, scriptIdOrCode, params))
                        .setRefreshPolicy(esWriteOptionVo.getRefreshPolicy())
                        .routing(esWriteOptionVo.getRouting())
                        .version(esWriteOptionVo.getVersion())
                        .versionType(esWriteOptionVo.getVersionType()));
            }
        }
        try {
            this.client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new EsException("es updateByScript batch error", e);
        }
    }

    protected Page<EsEntityVo<ENTITY>> buildSearchResponse(SearchResponse searchResponse, SearchQuery searchQuery) {
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

            if (!ObjectUtils.isEmpty(searchQuery.getSortValues()) || EsConstant.EMPTY_SORT_VALUES.equals(searchQuery.getSortValues())) {
                esEntityVo.setSortValues(hit.getSortValues());
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

    protected Map<String, List<EsValueCountVo>> buildAggregateResponse(SearchResponse searchResponse) {
        Map<String, List<EsValueCountVo>> resultMap = new LinkedHashMap<>();
        this.buildAggregateResponse(searchResponse.getAggregations(), resultMap);
        return resultMap;
    }

    protected void buildAggregateResponse(Aggregations aggregations, Map<String, List<EsValueCountVo>> resultMap) {
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
                this.buildAggregateResponse(subAggregations, resultMap);
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

    protected void buildIdsBulkResponse(BulkResponse bulkResponse, BatchUpdateRespVo<ID> respVo, Collection<ID> ids, String operation) {
        if (bulkResponse.hasFailures()) {
            Set<String> successIds = new HashSet<>();
            Set<String> errorIds = new HashSet<>();
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    errorIds.add(bulkItemResponse.getId());
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    log.error(respVo.getTraceId() + ",es {} error,id={}", operation, bulkItemResponse.getId(), failure.getCause());
                } else {
                    successIds.add(bulkItemResponse.getId());
                }
            }
            log.error(respVo.getTraceId() + ",es {} error,errorCount={},totalCount={}", operation, errorIds.size(), errorIds.size() + successIds.size());
            if (successIds.size() > 0) {
                respVo.getSuccessItems().addAll(ids.stream().filter(o -> o != null && successIds.contains(String.valueOf(o))).collect(Collectors.toList()));
            }
            if (errorIds.size() > 0) {
                respVo.getErrorItems().addAll(ids.stream().filter(o -> o != null && errorIds.contains(String.valueOf(o))).collect(Collectors.toList()));
            }
        } else {
            respVo.getSuccessItems().addAll(ids);
        }
    }

    protected void buildEntitiesBulkResponse(BulkResponse bulkResponse, BatchUpdateRespVo<ENTITY> respVo, Collection<ENTITY> entities, String operation) {
        if (bulkResponse.hasFailures()) {
            Set<String> successIds = new HashSet<>();
            Set<String> errorIds = new HashSet<>();
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    errorIds.add(bulkItemResponse.getId());
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    log.error(respVo.getTraceId() + ",es {} error,id={}", operation, bulkItemResponse.getId(), failure.getCause());
                } else {
                    successIds.add(bulkItemResponse.getId());
                }
            }
            log.error(respVo.getTraceId() + ",es {} error,errorCount={},totalCount={}", operation, errorIds.size(), errorIds.size() + successIds.size());
            if (successIds.size() > 0) {
                respVo.getSuccessItems().addAll(entities.stream().filter(o -> o != null && successIds.contains(String.valueOf(this.getId(o)))).collect(Collectors.toList()));
            }
            if (errorIds.size() > 0) {
                respVo.getErrorItems().addAll(entities.stream().filter(o -> o != null && errorIds.contains(String.valueOf(this.getId(o)))).collect(Collectors.toList()));
            }
        } else {
            respVo.getSuccessItems().addAll(entities);
        }
    }

    protected ID getId(ENTITY entity) {

        try {
            return (ID) this.idField.get(entity);
        } catch (IllegalAccessException e) {
            throw new EsException("get id error", e);
        }
    }
}
