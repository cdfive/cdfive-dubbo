package com.cdfive.es.query.build;

import com.cdfive.common.util.JacksonUtil;

/**
 * @author cdfive
 */
public interface QueryParameter {

    default <S> QueryParameter from(S source) {
        return JacksonUtil.jsonToObj(JacksonUtil.objToJson(source), QueryParameter.class);
    }
}
