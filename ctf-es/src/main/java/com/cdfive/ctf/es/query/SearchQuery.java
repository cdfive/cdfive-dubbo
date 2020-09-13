package com.cdfive.ctf.es.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cdfive
 */
public class SearchQuery implements Serializable {

    private QueryBuilder query;

    private List<SortBuilder> sorts = new ArrayList<>();

    private List<String> fields = new ArrayList<>();

    private Pageable pageable;

    public SearchQuery() {

    }

    public SearchQuery(QueryBuilder query) {
        this.query = query;
    }

    public SearchQuery(QueryBuilder query, Pageable pageable) {
        this.query = query;
        this.pageable = pageable;
    }

    public SearchQuery(QueryBuilder query, List<SortBuilder> sorts, Pageable pageable) {
        this.query = query;
        this.sorts = sorts;
        this.pageable = pageable;
    }

    public SearchQuery(QueryBuilder query, List<SortBuilder> sorts, List<String> fields, Pageable pageable) {
        this.query = query;
        this.sorts = sorts;
        this.fields = fields;
        this.pageable = pageable;
    }

    public SearchQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public SearchQuery withSort(SortBuilder sort) {
        this.sorts.add(sort);
        return this;
    }

    public SearchQuery withPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }

    public SearchQuery withField(String field) {
        addField(field);
        return this;
    }

    public SearchQuery withFields(List<String> fields) {
        addFields(fields);
        return this;
    }

    public SearchQuery withFields(String... addFields) {
        addFields(addFields);
        return this;
    }

    public SearchQuery addField(String field) {
        this.fields.add(field);
        return this;
    }

    public SearchQuery addFields(String... addFields) {
        if (addFields == null || addFields.length == 0) {
            return this;
        }

        this.fields.addAll(Arrays.asList(addFields));
        return this;
    }

    public SearchQuery addFields(List<String> fields) {
        if(CollectionUtils.isEmpty(fields)) {
            return this;
        }

        this.fields.addAll(fields);
        return this;
    }

    public SearchQuery addSort(SortBuilder sort) {
        this.sorts.add(sort);
        return this;
    }

    public QueryBuilder getQuery() {
        return query;
    }

    public SearchQuery setQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public List<SortBuilder> getSorts() {
        return sorts;
    }

    public SearchQuery setSorts(List<SortBuilder> sorts) {
        this.sorts = sorts;
        return this;
    }

    public List<String> getFields() {
        return fields;
    }

    public SearchQuery setFields(List<String> fields) {
        this.fields = fields;
        return this;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public SearchQuery setPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }
}
