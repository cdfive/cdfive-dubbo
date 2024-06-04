package com.cdfive.es.query.build.impl;

import com.cdfive.common.util.CollectionUtil;
import com.cdfive.es.query.AggregateQuery;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.query.build.QueryBuilder;
import com.cdfive.es.query.build.QueryBuilderContext;
import com.cdfive.es.query.build.QueryHandler;
import com.cdfive.es.query.build.QueryParameter;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author cdfive
 */
public abstract class BaseQueryBuilder<Param extends QueryParameter> implements QueryBuilder<Param> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected String searchType;

    private BaseQueryBuilder<Param> nextQueryBuilder;

    private QueryHandler queryHandler;

    public BaseQueryBuilder<Param> withSearchType(String searchType) {
        this.searchType = searchType;
        return this;
    }


    public BaseQueryBuilder<Param> withBuilder(BaseQueryBuilder<Param> nextQueryBuilder) {
        Assert.notNull(nextQueryBuilder, "nextQueryBuilder can't be null");

        if (this.nextQueryBuilder != null) {
            this.nextQueryBuilder.withBuilder(nextQueryBuilder);
        } else {
            this.nextQueryBuilder = nextQueryBuilder;
        }
        return this;
    }

    public BaseQueryBuilder<Param> withHandler(QueryHandler queryHandler) {
        this.queryHandler = queryHandler;
        return this;
    }

    @Override
    public void build(QueryBuilderContext context, Param param) {
        if (context.isSkip()) {
            return;
        }

        doBuild(context, param);

        if (this.nextQueryBuilder != null) {
            this.nextQueryBuilder.build(context, param);
        }
    }

    @Override
    public SearchQuery buildSearchQuery(Param param) {
        QueryBuilderContext context = new QueryBuilderContext();

        this.build(context, param);

        if (context.isSkip()) {
            return null;
        }

        SearchQuery searchQuery = this.buildSearchQuery(context);

        queryHandler.handle(searchQuery, param);
        return searchQuery;
    }

    @Override
    public AggregateQuery buildAggregateQuery(Param param) {
        QueryBuilderContext context = new QueryBuilderContext();

        this.build(context, param);

        if (context.isSkip()) {
            return null;
        }

        AggregateQuery aggregateQuery = this.buildAggregateQuery(context);

        queryHandler.handle(aggregateQuery, param);
        return aggregateQuery;
    }

    abstract protected void doBuild(QueryBuilderContext context, Param param);

    private SearchQuery buildSearchQuery(QueryBuilderContext context) {
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.withQuery(this.buildEsQueryBuilder(context));
        return searchQuery;
    }

    private AggregateQuery buildAggregateQuery(QueryBuilderContext context) {
        AggregateQuery aggregateQuery = new AggregateQuery();
        aggregateQuery.withQuery(this.buildEsQueryBuilder(context));
        return aggregateQuery;
    }

    private org.elasticsearch.index.query.QueryBuilder buildEsQueryBuilder(QueryBuilderContext context) {
        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = context.getFilterFunctionBuilders();
        if (!CollectionUtil.isEmpty(filterFunctionBuilders)) {
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(context.getRootQueryBuilder(), filterFunctionBuilders.toArray(new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{}));
            functionScoreQueryBuilder.scoreMode(FunctionScoreQuery.ScoreMode.SUM).boostMode(CombineFunction.SUM);
            return functionScoreQueryBuilder;
        }

        return context.getRootQueryBuilder();
    }

    public String getSearchType() {
        return searchType;
    }

    public BaseQueryBuilder<Param> getNextQueryBuilder() {
        return nextQueryBuilder;
    }

    public QueryHandler getQueryHandler() {
        return queryHandler;
    }
}
