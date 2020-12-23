package com.cdfive.ctf.redis;

/**
 * @author cdfive
 */
public interface RedisKeyCallback {

    void doCallback(String key);
}
