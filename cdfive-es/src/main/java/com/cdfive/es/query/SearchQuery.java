package com.cdfive.es.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author cdfive
 * @date 2022-01-05
 */
@SuppressWarnings("rawtypes")
public class SearchQuery implements Serializable {

    private static final long serialVersionUID = -4566279863200250787L;

    // 查询
    private QueryBuilder query;

    // 排序
    private List<SortBuilder> sorts;

    // 返回字段列表
    private List<String> fields;

    // 返回脚本字段列表
    private List<SearchSourceBuilder.ScriptField> scriptFields;

    // 分页
    private Pageable pageable;

    // 折叠
    private CollapseBuilder collapse;

    // 聚合列表
    private List<AggregationBuilder> aggregations;

    // 是否获取总数
    private boolean trackTotalHits = false;

    // 是否查询版本号
    private boolean version = false;

    // 是否返回打分
    private boolean score = false;

    // sort values for search after
    private Object[] sortValues = null;

    public static SearchQuery of() {
        return new SearchQuery();
    }

    public static SearchQuery of(QueryBuilder query) {
        return new SearchQuery(query);
    }

    public static SearchQuery of(QueryBuilder query, Pageable pageable) {
        return new SearchQuery(query, pageable);
    }

    public static SearchQuery of(QueryBuilder query, List<SortBuilder> sorts, Pageable pageable) {
        return new SearchQuery(query, sorts, pageable);
    }

    public static SearchQuery of(QueryBuilder query, List<SortBuilder> sorts, List<String> fields, Pageable pageable) {
        return new SearchQuery(query, sorts, fields, pageable);
    }

    public static SearchQuery of(QueryBuilder query, List<SortBuilder> sorts, List<String> fields, List<SearchSourceBuilder.ScriptField> scriptFields, Pageable pageable) {
        return new SearchQuery(query, sorts, fields, scriptFields, pageable);
    }

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

    public SearchQuery(QueryBuilder query, List<SortBuilder> sorts, List<String> fields, List<SearchSourceBuilder.ScriptField> scriptFields, Pageable pageable) {
        this.query = query;
        this.sorts = sorts;
        this.fields = fields;
        this.scriptFields = scriptFields;
        this.pageable = pageable;
    }

    public SearchSourceBuilder toSearchSourceBuilder() {
        QueryBuilder queryBuilder = this.getQuery();
        Pageable pageable = this.getPageable();
        if (pageable == null) {
            pageable = Pageable.unpaged();
            this.setPageable(pageable);
        }

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);

        List<SortBuilder> sorts = this.getSorts();
        if (!CollectionUtils.isEmpty(sorts)) {
            for (SortBuilder sort : sorts) {
                searchSourceBuilder.sort(sort);
            }
        }

        if (pageable.isPaged()) {
            searchSourceBuilder.from((int) pageable.getOffset());
            searchSourceBuilder.size(pageable.getPageSize());
        }

        if (this.isTrackTotalHits()) {
            searchSourceBuilder.trackTotalHits(true);
        }

        if (this.isVersion()) {
            searchSourceBuilder.version(true);
        }

        List<String> fields = this.getFields();
        if (!CollectionUtils.isEmpty(fields)) {
            FetchSourceContext sourceContext = new FetchSourceContext(true, fields.toArray(new String[]{}), null);
            searchSourceBuilder.fetchSource(sourceContext);
        }

        if (!CollectionUtils.isEmpty(scriptFields)) {
            for (SearchSourceBuilder.ScriptField scriptField : scriptFields) {
                searchSourceBuilder.scriptField(scriptField.fieldName(), scriptField.script(), scriptField.ignoreFailure());
            }
        }

        if (this.getCollapse() != null) {
            searchSourceBuilder.collapse(this.getCollapse());
        }

        if (!CollectionUtils.isEmpty(this.getAggregations())) {
            List<AggregationBuilder> aggregations = this.getAggregations();
            for (AggregationBuilder aggregation : aggregations) {
                searchSourceBuilder.aggregation(aggregation);
            }
        }

        if (!ObjectUtils.isEmpty(this.getSortValues())) {
            searchSourceBuilder.from(0);
            searchSourceBuilder.searchAfter(this.getSortValues());
        }

        return searchSourceBuilder;
    }

    public SearchQuery withQuery(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public SearchQuery withSort(SortBuilder sort) {
        if (this.sorts == null) {
            this.sorts = new ArrayList<>();
        }

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
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }

        this.fields.add(field);
        return this;
    }

    public SearchQuery addFields(String... addFields) {
        if (addFields == null || addFields.length == 0) {
            return this;
        }

        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }

        this.fields.addAll(Arrays.asList(addFields));
        return this;
    }

    public SearchQuery addFields(List<String> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return this;
        }

        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }

        this.fields.addAll(fields);
        return this;
    }

    public SearchQuery withScriptField(String name, Script script) {
        return withScriptField(name, script, false);
    }

    public SearchQuery withScriptField(String name, Script script, boolean ignoreFailure) {
        if (scriptFields == null) {
            this.scriptFields = new ArrayList<>();
        }
        this.scriptFields.add(new SearchSourceBuilder.ScriptField(name, script, ignoreFailure));
        return this;
    }

    public SearchQuery withScriptField(String name, String script, Map<String, Object> params) {
        return withScriptField(name, script, params, false);
    }

    public SearchQuery withScriptField(String name, String script, Map<String, Object> params, boolean ignoreFailure) {
        return withScriptField(name, new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, script, params), ignoreFailure);
    }

    public SearchQuery addScriptField(String name, Script script) {
        return addScriptField(name, script, false);
    }

    public SearchQuery addScriptField(String name, Script script, boolean ignoreFailure) {
        if (scriptFields == null) {
            this.scriptFields = new ArrayList<>();
        }
        this.scriptFields.add(new SearchSourceBuilder.ScriptField(name, script, ignoreFailure));
        return this;
    }

    public SearchQuery addScriptField(String name, String script, Map<String, Object> params) {
        return addScriptField(name, script, params, false);
    }

    public SearchQuery addScriptField(String name, String script, Map<String, Object> params, boolean ignoreFailure) {
        return addScriptField(name, new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, script, params), ignoreFailure);
    }

    public SearchQuery addSort(SortBuilder sort) {
        if (this.sorts == null) {
            this.sorts = new ArrayList<>();
        }

        this.sorts.add(sort);
        return this;
    }

    public SearchQuery withCollapse(CollapseBuilder collapse) {
        this.collapse = collapse;
        return this;
    }

    public SearchQuery withAggregation(AggregationBuilder aggregation) {
        if (this.aggregations == null) {
            this.aggregations = new ArrayList<>();
        }

        this.aggregations.add(aggregation);
        return this;
    }

    public SearchQuery trackTotalHits(boolean trackTotalHits) {
        this.trackTotalHits = trackTotalHits;
        return this;
    }

    public SearchQuery version(boolean version) {
        this.version = version;
        return this;
    }

    public SearchQuery score(boolean score) {
        this.score = score;
        return this;
    }

    public SearchQuery sortValues(Object[] sortValues) {
        this.sortValues = sortValues;
        return this;
    }

    public QueryBuilder getQuery() {
        return query;
    }

    public void setQuery(QueryBuilder query) {
        this.query = query;
    }

    public List<SortBuilder> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortBuilder> sorts) {
        this.sorts = sorts;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<SearchSourceBuilder.ScriptField> getScriptFields() {
        return scriptFields;
    }

    public void setScriptFields(List<SearchSourceBuilder.ScriptField> scriptFields) {
        this.scriptFields = scriptFields;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public CollapseBuilder getCollapse() {
        return collapse;
    }

    public void setCollapse(CollapseBuilder collapse) {
        this.collapse = collapse;
    }

    public List<AggregationBuilder> getAggregations() {
        return aggregations;
    }

    public void setAggregations(List<AggregationBuilder> aggregations) {
        this.aggregations = aggregations;
    }

    public boolean isTrackTotalHits() {
        return trackTotalHits;
    }

    public void setTrackTotalHits(boolean trackTotalHits) {
        this.trackTotalHits = trackTotalHits;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isScore() {
        return score;
    }

    public void setScore(boolean score) {
        this.score = score;
    }

    public Object[] getSortValues() {
        return sortValues;
    }

    public void setSortValues(Object[] sortValues) {
        this.sortValues = sortValues;
    }
}
