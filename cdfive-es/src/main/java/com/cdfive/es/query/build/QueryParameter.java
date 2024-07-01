package com.cdfive.es.query.build;


import com.cdfive.es.util.JacksonUtil;

/**
 * @author cdfive
 */
public interface QueryParameter {

    public static <Param extends QueryParameter, S> Param from(S source, Class<Param> clazz) {
        return JacksonUtil.jsonToObj(JacksonUtil.objToJson(source), clazz);
    }
}
