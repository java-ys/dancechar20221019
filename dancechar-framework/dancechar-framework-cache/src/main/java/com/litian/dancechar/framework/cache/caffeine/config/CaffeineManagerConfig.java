package com.litian.dancechar.framework.cache.caffeine.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine manager配置
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
@Component
public class CaffeineManagerConfig {
    @Resource
    private CaffeineConfig caffeineConfig;

    @Bean(name = "caffeine")
    public CacheManager initCacheManager(){
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder().initialCapacity(
                Convert.toInt(caffeineConfig.getInitCapacity(), 100))
                .maximumSize(Convert.toInt(caffeineConfig.getMaxSize(), 1000))
                .expireAfterAccess(Convert.toInt(caffeineConfig.getExpireAfterAccess(), 1000),
                        TimeUnit.SECONDS);
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.setCacheNames(StrUtil.isEmpty(caffeineConfig.getCacheNames()) ?
                Lists.newArrayList("commCache") : Arrays.asList(caffeineConfig.getCacheNames().split(";")));
        caffeineCacheManager.setAllowNullValues(false);
        return caffeineCacheManager;
    }

    @Bean
    public CacheLoader<Object, Object> cacheLoader(){
        return new CacheLoader<Object, Object>() {
            @Override
            public  Object load(Object o) throws Exception {
                return null;
            }

            public Object reload(Object key, Object oldValue){
                // 重写这个方法将oldValue值返回，然后刷新缓存
                return oldValue;
            }
        };
    }
}
