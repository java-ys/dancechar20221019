package com.litian.dancechar.framework.cache.caffeine.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine本地缓存配置
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "caffeine", ignoreInvalidFields = true)
public class CaffeineConfig {

    /**
     * cache name配置
     */
    private String cacheNames;

    /**
     * 初始的缓存空间大小
     */
    private String initCapacity;

    /**
     * 缓存最大的条数
     */
    private String maxSize;

    /**
     * 最后一次写入或访问后经过固定的时间
     */
    private String expireAfterAccess;

    /**
     * 创建缓存或最新一次更新缓存后经过固定的时间间隔，刷新缓存
     */
    private String refreshAfterWrite;
}
