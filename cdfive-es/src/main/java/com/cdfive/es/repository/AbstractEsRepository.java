package com.cdfive.es.repository;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.util.CollectionUtil;
import com.cdfive.common.util.GenericClassUtil;
import com.cdfive.common.util.StringUtil;
import com.cdfive.es.annotation.Document;
import com.cdfive.es.config.EsProperties;
import com.cdfive.es.constant.EsConstant;
import com.cdfive.es.entity.Scoreable;
import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.DeleteQuery;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.query.UpdateQuery;
import com.cdfive.es.vo.ValueCountVo;
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
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.UnmappedTerms;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cdfive
 */
public abstract class AbstractEsRepository<Entity, Id> implements EsRepository<Entity, Id>, InitializingBean {

    protected Logger log = LoggerFactory.getLogger(getClass());

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
            throw new RuntimeException("es update but missing id");
        }

        if (CollectionUtils.isEmpty(params)) {
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
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(index);
        QueryBuilder query = updateQuery.getQuery();
        updateByQueryRequest.setQuery(query);

        Integer batchSize = updateQuery.getBatchSize();
        if (batchSize != null) {
            updateByQueryRequest.setBatchSize(batchSize);
        }

        if (!StringUtils.isEmpty(updateQuery.getScript())) {
            Script inline = new Script(ScriptType.INLINE, EsConstant.LANG_PAINLESS, updateQuery.getScript(), updateQuery.getParams());
            updateByQueryRequest.setScript(inline);
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
        Script inline = new Script(ScriptType.INLINE, EsConstant.LANG_PAINLESS, script, params);
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

        if (StringUtils.isEmpty(script)) {
            throw new RuntimeException("es updateByScript batch but empty script");
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
            bulkRequest.add(new UpdateRequest(index, idsIterator.next().toString()).script(new Script(ScriptType.INLINE, EsConstant.LANG_PAINLESS, script, paramsIterator.next())));
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

        if (StringUtils.isEmpty(script)) {
            throw new RuntimeException("es updateByScript batch but empty script");
        }

        if (CollectionUtils.isEmpty(params)) {
            throw new RuntimeException("es updateByScript batch but empty params");
        }

        BulkRequest bulkRequest = new BulkRequest();
        int size = ids.size();
        Iterator<Id> idsIterator = ids.iterator();
        for (int i = 0; i < size; i++) {
            bulkRequest.add(new UpdateRequest(index, idsIterator.next().toString()).script(new Script(ScriptType.INLINE, EsConstant.LANG_PAINLESS, script, params)));
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
        SearchSourceBuilder searchSourceBuilder = searchQuery.toSearchSourcebuilder();
        if (esProperties.getTrackTotalHits() != null && esProperties.getTrackTotalHits()) {
            searchSourceBuilder.trackTotalHits(true);
        }

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);

        if (log.isDebugEnabled()) {
            log.debug("searchDsl=>" + searchSourceBuilder.toString());
        }

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
        if (!CollectionUtil.isEmpty(searchQuery.getAggregations())) {
            try {
                total = ((ParsedCardinality) searchResponse.getAggregations().asList().get(0)).getValue();
            } catch (Exception e) {
                log.error("parse total from aggregations error", e);
            }
        }
        if (total == 0) {
            return new PageImpl<>(Collections.emptyList(), searchQuery.getPageable(), 0);
        }

        List<Entity> entities = new ArrayList<>();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            Entity entity = JSON.parseObject(source, entityClass);

            if (searchQuery.isScore()) {
                if (entity instanceof Scoreable) {
                    ((Scoreable) entity).setScore(hit.getScore());
                }
            }
            entities.add(entity);
        }

        return new PageImpl<>(entities, searchQuery.getPageable(), total);
    }

    @Override
    public Map<String, List<ValueCountVo>> aggregate(AggregateQuery aggregateQuery) {
        SearchSourceBuilder searchSourceBuilder = aggregateQuery.toSearchSourcebuilder();

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es aggregate error", e);
        }

        if (!RestStatus.OK.equals(searchResponse.status())) {
            throw new RuntimeException("es aggregate fail,status=" + searchResponse.status());
        }

        Map<String, List<ValueCountVo>> resultMap = new HashMap<>();
        this.buildMapFromAggregations(searchResponse.getAggregations(), resultMap);
        return resultMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        entityClass = GenericClassUtil.getGenericTypeFromSuperClass(this.getClass(), 0);
        Document document = AnnotationUtils.findAnnotation(entityClass, Document.class);
        if (document == null) {
            throw new RuntimeException(entityClass.getName() + " must be with annotation:'com.cdfive.es.annotation.Document'");
        }
        index = document.index();
        if (StringUtil.isBlank(index)) {
            throw new RuntimeException("index must not be blank,check index property of com.cdfive.es.annotation.Document");
        }

        for (Field field : entityClass.getDeclaredFields()) {
            com.cdfive.es.annotation.Id id = field.getAnnotation(com.cdfive.es.annotation.Id.class);
            if (id != null) {
                if (idField != null) {
                    throw new RuntimeException("Duplicate @com.cdfive.es.annotation.Id field in " + entityClass.getName());
                }
                idField = field;
                idField.setAccessible(true);
            }
        }
        if (idField == null) {
            throw new RuntimeException("Missing @com.cdfive.es.annotation.Id field in " + entityClass.getName());
        }
    }

    protected Id getId(Entity entity) {
        try {
            return (Id) idField.get(entity);
        } catch (Exception e) {
            throw new RuntimeException("getId error in " + entityClass.getName(), e);
        }
    }

    private void buildMapFromAggregations(Aggregations aggregations, Map<String, List<ValueCountVo>> resultMap) {
        if (aggregations == null) {
            return;
        }

        Map<String, Aggregation> aggregationMap = aggregations.asMap();
        if (CollectionUtils.isEmpty(aggregationMap)) {
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
                this.buildMapFromAggregations(subAggregations, resultMap);
            } else if (aggregation instanceof ParsedLongTerms) {
                List<? extends Terms.Bucket> buckets = ((ParsedLongTerms) aggregation).getBuckets();
                if (CollectionUtils.isEmpty(buckets)) {
                    continue;
                }

                resultMap.put(key, buckets.stream()
                        .map(o -> new ValueCountVo(o.getKeyAsString(), o.getDocCount()))
                        .collect(Collectors.toList()));
            } else if (aggregation instanceof ParsedCardinality) {
                ParsedCardinality parsedCardinality = (ParsedCardinality) aggregation;
                resultMap.put(key, Stream.of(new ValueCountVo(parsedCardinality.getName(), parsedCardinality.getValue())).collect(Collectors.toList()));
            } else {
                log.error("unsupport aggregation type,type={}", aggregation.getClass().getName());
                throw new RuntimeException("buildMapFromAggregations error");
            }
        }
    }
}
