package com.litian.dancechar.framework.cache.caffeine.util;

import com.litian.dancechar.framework.cache.caffeine.config.CaffeineConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * caffeine本地缓存操作工具类
 *
 * @author tojson
 * @date 2022/03/21 16:20
 */
@Slf4j
@Configuration
@Order(2)
@Import(CaffeineConfig.class)
public class CaffeineCacheUtil {
    @Resource
    private CacheManager caffeineCacheManager;

    /**
     * 获取缓存
     */
    public Object getCache(String cacheName, Object key){
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if(null == cache){
            return null;
        }
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if(null == valueWrapper){
            return null;
        }
        return valueWrapper.get();
    }

    /**
     * 添加缓存
     */
    public void putCache(String cacheName, Object key, Object value) {
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if(null == cache){
            String errMsg = String.format("缓存cacheName未配置，请检查！cacheName:%s", cacheName);
            log.error(errMsg);
            return;
        }
        CaffeineCache caffeineCache = (CaffeineCache)cache;
        caffeineCache.put(key, value);
    }

    /**
     * 清空缓存
     */
    public void clearCache(String cacheName){
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if(cache != null){
            cache.clear();
        }
    }

    /**
     * 失效缓存
     */
    public void evictCache(String cacheName, Object key){
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if(null == cache){
            String errMsg = String.format("缓存cacheName未配置，请检查！cacheName:%s", cacheName);
            log.error(errMsg);
            return;
        }
        cache.evict(key);
    }

    /**
     * 获取所有的缓存Name
     */
    public List<String> cacheNames(){
        return new ArrayList<>(caffeineCacheManager.getCacheNames());
    }
}