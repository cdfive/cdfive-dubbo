package com.cdfive.es.util;

import com.cdfive.es.query.SearchQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.slf4j.Logger;
import org.springframework.util.Assert;

/**
 * @author cdfive
 */
public class EsUtil {

    public static String genDsl(SearchQuery searchQuery) {
        return searchQuery.toSearchSourcebuilder().toString();
    }
}
