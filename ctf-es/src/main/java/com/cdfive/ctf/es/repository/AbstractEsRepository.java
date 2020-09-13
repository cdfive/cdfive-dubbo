package com.cdfive.ctf.es.repository;

import com.alibaba.fastjson.JSON;
import com.cdfive.common.util.GenericClassUtil;
import com.cdfive.ctf.es.annotation.Document;
import com.cdfive.ctf.es.query.DeleteQuery;
import com.cdfive.ctf.es.query.SearchQuery;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
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

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author cdfive
 */
public abstract class AbstractEsRepository<Entity, Id> implements EsRepository<Entity, Id>, InitializingBean {

    @Autowired
    protected RestHighLevelClient client;

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
            searchSourceBuilder.trackTotalHits(true);
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
