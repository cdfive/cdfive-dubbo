package com.cdfive.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author cdfive
 */
public interface RedisBatchKeysCallback {

    void doCallback(Jedis jedis, List<String> keys);
}
