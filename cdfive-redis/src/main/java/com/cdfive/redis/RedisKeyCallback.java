package com.cdfive.redis;

import redis.clients.jedis.Jedis;

/**
 * @author cdfive
 */
public interface RedisKeyCallback {

    void doCallback(Jedis jedis, String key);
}
