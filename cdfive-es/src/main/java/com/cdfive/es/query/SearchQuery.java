package com.cdfive.es.query;

import com.cdfive.es.constant.EsConstant;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
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

    private CollapseBuilder collapse;

    private List<SortBuilder> sorts = new ArrayList<>();

    private List<String> fields = new ArrayList<>();

    private Pageable pageable = Pageable.unpaged();

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

    public SearchQuery withCollapse(CollapseBuilder collapse) {
        this.collapse = collapse;
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

    public CollapseBuilder getCollapse() {
        return collapse;
    }

    public void setCollapse(CollapseBuilder collapse) {
        this.collapse = collapse;
    }

    public SearchSourceBuilder toSearchSourcebuilder() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(this.getQuery());

        List<SortBuilder> sorts = this.getSorts();
        if (!CollectionUtils.isEmpty(sorts)) {
            for (SortBuilder sort : sorts) {
                searchSourceBuilder.sort(sort);
            }
        }

        Pageable pageable = this.getPageable();
        if (pageable.isPaged()) {
            if (pageable.getOffset() > EsConstant.MAX_RESULT) {
                searchSourceBuilder.from(EsConstant.MAX_RESULT.intValue());
                searchSourceBuilder.size(0);
            } else {
                searchSourceBuilder.from((int) pageable.getOffset());
                if ((int) pageable.getOffset() + pageable.getPageSize() > EsConstant.MAX_RESULT.intValue()) {
                    searchSourceBuilder.size(EsConstant.MAX_RESULT.intValue() - (int) pageable.getOffset());
                } else {
                    searchSourceBuilder.size(pageable.getPageSize());
                }
            }
        }

        List<String> fields = this.getFields();
        if (!CollectionUtils.isEmpty(fields)) {
            FetchSourceContext sourceContext = new FetchSourceContext(true, fields.toArray(new String[]{}), null);
            searchSourceBuilder.fetchSource(sourceContext);
        }

        if (this.collapse != null) {
            searchSourceBuilder.collapse(collapse);
        }

        return searchSourceBuilder;
    }
}
