package com.cdfive.common.service;

/**
 * @author cdfive
 */
public interface IdService {

    Long nextLongId();

    String nextUUID();
}
