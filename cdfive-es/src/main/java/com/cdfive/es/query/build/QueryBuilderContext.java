package com.cdfive.es.query.build;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 */
public class QueryBuilderContext {

    private boolean skip;

    private BoolQueryBuilder rootQueryBuilder;

    private List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders;

    public QueryBuilderContext() {
        this.skip = false;
        this.rootQueryBuilder = QueryBuilders.boolQuery();
        this.filterFunctionBuilders = new ArrayList<>();
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public BoolQueryBuilder getRootQueryBuilder() {
        return rootQueryBuilder;
    }

    public List<FunctionScoreQueryBuilder.FilterFunctionBuilder> getFilterFunctionBuilders() {
        return filterFunctionBuilders;
    }
}
