package com.cdfive.es.util;

import com.cdfive.es.query.SearchQuery;

/**
 * @author cdfive
 */
public class EsUtil {

    public static String genDsl(SearchQuery searchQuery) {
        return searchQuery.toSearchSourceBuilder().toString();
    }
}
