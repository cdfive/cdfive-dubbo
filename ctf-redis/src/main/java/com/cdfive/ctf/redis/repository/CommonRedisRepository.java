package com.cdfive.ctf.redis.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
public class CommonRedisRepository implements CommonRedisRepositoryApi<ShardedJedis> {

    protected static final String RESULT_OK = "OK";

    protected static final Long RESULT_ONE = 1L;

    protected static final String NX = "NX";

    protected static final String XX = "XX";

    protected static final String EX = "EX";

    protected static final String PX = "PX";

    @Setter
    @Autowired
    protected ShardedJedisPool pool;

    @Override
    public Pool<ShardedJedis> getPool() {
        return pool;
    }

    @Override
    public Long ttl(String key) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.ttl(key);
        }
    }

    @Override
    public Long ttlMs(String key) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.pttl(key);
        }
    }

    @Override
    public boolean exists(String key) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.exists(key);
        }
    }

    @Override
    public boolean del(String key) {
        try (ShardedJedis jedis = pool.getResource()) {
            Long result = jedis.del(key);
            return RESULT_ONE.equals(result);
        }
    }

    @Override
    public boolean expire(String key, int second) {
        try (ShardedJedis jedis = pool.getResource()) {
            Long result = jedis.expire(key, second);
            return RESULT_ONE.equals(result);
        }
    }

    @Override
    public boolean expireMs(String key, int milliSecond) {
        try (ShardedJedis jedis = pool.getResource()) {
            Long result = jedis.pexpire(key, milliSecond);
            return RESULT_ONE.equals(result);
        }
    }

    @Override
    public String get(String key) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public boolean set(String key, String value) {
        try(ShardedJedis jedis = pool.getResource()) {
            String result = jedis.set(key, value);
            return RESULT_OK.equals(result);
        }
    }

    @Override
    public boolean setex(String key, String value, int second) {
        try (ShardedJedis jedis = pool.getResource()) {
            String result = jedis.setex(key, second, value);
            return RESULT_OK.equals(result);
        }
    }

    @Override
    public boolean setnx(String key, String value) {
        try (ShardedJedis jedis = pool.getResource()) {
            Long result = jedis.setnx(key, value);
            return RESULT_ONE.equals(result);
        }
    }

    @Override
    public boolean lock(String key, String value, int second) {
        try (ShardedJedis jedis = pool.getResource()) {
            String result = jedis.set(key, value, NX, EX, second);
            return RESULT_OK.equals(result);
        }
    }

    @Override
    public boolean lockMs(String key, String value, int milliSecond) {
        try (ShardedJedis jedis = pool.getResource()) {
            String result = jedis.set(key, value, NX, PX, milliSecond);
            return RESULT_OK.equals(result);
        }
    }

    @Override
    public boolean unlock(String key) {
        return del(key);
    }

    @Override
    public String hget(String key, String field) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.hget(key, field);
        }
    }

    @Override
    public boolean hset(String key, String filed, String value) {
        try (ShardedJedis jedis = pool.getResource()) {
            Long result = jedis.hset(key, filed, value);
            return RESULT_ONE.equals(result);
        }
    }

    @Override
    public boolean hsetnx(String key, String field, String value) {
        try (ShardedJedis jedis = pool.getResource()) {
            Long result = jedis.hsetnx(key, field, value);
            return RESULT_ONE.equals(result);
        }
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.hmget(key, fields);
        }
    }

    @Override
    public boolean hmset(String key, Map<String, String> values) {
        try (ShardedJedis jedis = pool.getResource()) {
            String result = jedis.hmset(key, values);
            return RESULT_OK.equals(result);
        }
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.hgetAll(key);
        }
    }

    @Override
    public Long hlen(String key) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.hlen(key);
        }
    }

    @Override
    public boolean hexists(String key, String field) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.hexists(key, field);
        }
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        try (ShardedJedis jedis = pool.getResource()) {
            return jedis.hincrBy(key, field, value);
        }
    }

    @Override
    public boolean limitRate(String key, int second, int limit) {
        try (ShardedJedis jedis = pool.getResource()) {
            ShardedJedisPipeline pipeline = jedis.pipelined();
            long now = System.currentTimeMillis();
//            pipeline.zadd(key, now, now + "");
//            String member = UUID.randomUUID().toString();
//            pipeline.zadd(key, now, member);

//            String member = UUID.randomUUID().toString();
//            jedis.zadd(key, now, member);

            pipeline.zremrangeByScore(key, 0, now - second * 1000);

            Response<Long> zcardResp = pipeline.zcard(key);

            pipeline.expire(key, second + 1);
            pipeline.sync();

            Long count = zcardResp.get();

//            return count <= limit;
//            System.out.println(now + "=== " + zremResp.get() + "," + count);
            if (count < limit) {
                String member = UUID.randomUUID().toString();
                jedis.zadd(key, now, member);
                return true;
            }
//
////            jedis.zrem(key, member);
            return false;
        }
    }

    @Override
    public boolean limitPeriod(String key, int second, int limit) {
        try (ShardedJedis jedis = pool.getResource()) {
            Long result = jedis.incr(key);
            if (result == 1) {
                jedis.expire(key, second);
            }

//            System.out.println(result);
            return result <= limit;
        }
    }
}
