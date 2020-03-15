package com.cdfive.ctf.redis.repository;

import redis.clients.util.Pool;

import java.util.List;
import java.util.Map;

/**
 * @author cdfive
 */
public interface CommonRedisRepositoryApi<T> {

    Pool<T> getPool();

    boolean exists(String key);

    boolean del(String key);

    boolean expire(String key, int second);

    boolean expireMs(String key, int milliSecond);

    Long ttl(String key);

    Long ttlMs(String key);

    String get(String key);

    boolean set(String key, String value);

    boolean setex(String key, String value, int second);

    boolean setnx(String key, String value);

    boolean lock(String key, String value, int second);

    boolean lockMs(String key, String value, int milliSecond);

    boolean unlock(String key);

    String hget(String key, String field);

    boolean hset(String key, String filed, String value);

    boolean hsetnx(String key, String field, String value);

    List<String> hmget(String key, String... fields);

    boolean hmset(String key, Map<String, String> values);

    Map<String, String> hgetAll(String key);

    Long hlen(String key);

    boolean hexists(String key, String field);

    Long hincrBy(String key, String field, long value);

    boolean limitRate(String key, int second, int limit);
}
