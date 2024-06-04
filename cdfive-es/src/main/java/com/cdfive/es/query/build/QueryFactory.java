package com.cdfive.es.query.build;

import com.cdfive.es.query.build.impl.BaseQueryBuilder;
import com.cdfive.es.query.build.impl.StartQueryBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author cdfive
 */
public class QueryFactory<Param extends QueryParameter> {

    private final ConcurrentHashMap<String, BaseQueryBuilder<Param>> QUERY_BUILDER_MAP = new ConcurrentHashMap<>();

    public BaseQueryBuilder<Param> of(String searchType, Consumer<BaseQueryBuilder<Param>> initConsumer) {
        return QUERY_BUILDER_MAP.computeIfAbsent(searchType, (type) -> {
            BaseQueryBuilder<Param> queryBuilder = new StartQueryBuilder<Param>().withSearchType(type);
            initConsumer.accept(queryBuilder);
            return queryBuilder;
        });
    }
}
