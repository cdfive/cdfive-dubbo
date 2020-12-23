package com.cdfive.ctf.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfive
 */
@Slf4j
@EnableConfigurationProperties(RedisProperties.class)
@Configuration
public class RedisAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public ShardedJedisPool shardedJedisPool() {
        GenericObjectPoolConfig poolConfig = new JedisPoolConfig();

        String host = redisProperties.getHost();
        Integer port = redisProperties.getPort();
        Integer timeoutMs = redisProperties.getTimeoutMs();

        List<JedisShardInfo> shards = new ArrayList<>();
        JedisShardInfo shard = new JedisShardInfo(host, port, timeoutMs);
        String password = redisProperties.getPassword();
        if (!StringUtils.isEmpty(password)) {
            shard.setPassword(password);
        }
        shards.add(shard);

        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, shards);
        return shardedJedisPool;
    }
}
