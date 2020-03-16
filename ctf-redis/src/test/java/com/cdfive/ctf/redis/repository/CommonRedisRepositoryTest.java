package com.cdfive.ctf.redis.repository;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * @author cdfive
 */
public class CommonRedisRepositoryTest {

    private static CommonRedisRepository repository;

    @BeforeClass
    public static void beforeClass() {
        repository = new CommonRedisRepository();
        GenericObjectPoolConfig poolConfig = new JedisPoolConfig();

        String host = "localhost";
        int port = 6379;
        int timeoutMs = 1000;

        List<JedisShardInfo> shards = new ArrayList<>();
        JedisShardInfo shard = new JedisShardInfo(host, port, timeoutMs);
        shards.add(shard);
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, shards);

        repository.setPool(shardedJedisPool);

        repository.del("keyExists");
        repository.del("keyLimitRate");
        repository.del("keyLimitPeriod");
    }

    @Test
    public void testExists() {
        boolean result = repository.exists("keyExists");
        assertFalse(result);
    }

//    private static AtomicInteger passCount = new AtomicInteger(0);
    @Test
    public void testLimitRate() throws Exception {

//        new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//                passCount.set(0);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

        for (int i = 0; i < 1000; i++) {
//            boolean pass = repository.limitRate("keyLimitRate", 5, 10);
            boolean pass = repository.limitRate("keyLimitRate", 1, 5);
            if (pass) {
                System.out.println((i+1) + "=>" + pass);
            }

//            if (pass) {
//                int newPassCount = passCount.incrementAndGet();
//                assertTrue(newPassCount <= 5);
//            }

            TimeUnit.MILLISECONDS.sleep(Math.round(20));
        }
    }

    @Test
    public void testLimitPeriod() throws Exception {
        for (int i = 0; i < 1000; i++) {
//            boolean pass = repository.limitPeriod("keyLimitPeriod", 1, 5);
            boolean pass = repository.limitPeriod("keyLimitPeriod", 5, 20);
            if (pass) {
                System.out.println((i + 1) + "=>" + pass);
            }

            TimeUnit.MILLISECONDS.sleep(Math.round(20));
        }
    }
}
