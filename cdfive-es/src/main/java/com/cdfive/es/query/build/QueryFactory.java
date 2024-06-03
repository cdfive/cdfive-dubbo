package com.cdfive.es.query.build;

import com.cdfive.es.query.build.impl.BaseQueryBuilder;
import com.cdfive.es.query.build.impl.StartQueryBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author cdfive
 */
public class QueryFactory {

    private static final ConcurrentHashMap<String, BaseQueryBuilder> QUERY_BUILDER_MAP = new ConcurrentHashMap<>();

    private static final QueryFactory INSTANCE = new QueryFactory();

    private QueryFactory() {
    }

    public QueryFactory getInstance() {
        return INSTANCE;
    }

    public BaseQueryBuilder of(String searchType, Consumer<BaseQueryBuilder> initConsumer) {
        return QUERY_BUILDER_MAP.computeIfAbsent(searchType, (type) -> {
            BaseQueryBuilder queryBuilder = new StartQueryBuilder().withSearchType(type);
            initConsumer.accept(queryBuilder);
            return queryBuilder;
        });
    }
}
