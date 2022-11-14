package com.litian.dancechar.framework.cache.redis.bloomfilter.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 *
 * Bloom filter过滤器初始化相关配置
 *
 * @author tojson
 * @date 2022/10/15 23:53
 */
@Configuration
public class RedissonBloomFilerConfig {
    @Resource
    private RedissonClient redissonClient;

    @Bean
    public RBloomFilter<String> bloomFilter(){
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("dancecharRBloomFilter");
        bloomFilter.tryInit((long) 1E8,0.003);
        return bloomFilter;
    }
}
