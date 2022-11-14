package com.litian.dancechar.core.config;

import com.litian.dancechar.framework.cache.redis.bloomfilter.util.BloomFilerHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus配置
 *
 * @author tojson
 * @date 2021/6/19 21:13
 */
@Configuration
public class BloomFilterConfig {

    @Bean
    public BloomFilerHelper RedissonBloomFilerHelper() {
        return new BloomFilerHelper();
    }
}