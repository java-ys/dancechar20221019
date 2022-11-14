package com.litian.dancechar.framework.cache.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * 缓存通用工具类
 *
 * @author tojson
 * @date 2022/03/21 16:20
 */
@Component
public class CacheUtil {
    /**
     * 本地缓存变量
     */
    private static Map<String, ConcurrentMap<String, Object>> localCache = new HashMap<>(16);
    @Resource
    private RedisHelper redisHelper;

    /**
     * 获取缓存key中值,转换为list
     */
    public <T> List<T> getListForCacheName(String cacheName, String filed, Class<T> valueCls){
        ConcurrentMap<String,Object> concurrentMap  = localCache.get(cacheName);
        Object obj = concurrentMap.get(filed);
        if(ObjectUtil.isNotNull(obj)){
            return (List<T>)obj;
        }
        List<T> returnValue =  redisHelper.getList(filed, valueCls);
        if(CollUtil.isNotEmpty(returnValue)){
            concurrentMap.put(filed, returnValue);
        }
        return returnValue;
    }

    /**
     * 将数据放到本地缓存
     * @param cacheName  缓存名
     * @param filed  缓存filed
     * @param value  缓存value
     */
    public <T> void putLocalCache(String cacheName, String filed, T value){
        localCache.get(cacheName).put(filed, value);
    }

    /**
     * 将数据放到set集合本地缓存
     * @param cacheName  缓存名
     * @param filed  缓存filed
     * @param value  缓存value
     */
    public <T> void putSetValueToCache(String cacheName, String filed, T value){
        ConcurrentMap<String,Object> concurrentMap  = localCache.get(cacheName);
        Object obj = concurrentMap.get(filed);
        if(ObjectUtil.isNotNull(obj)){
            ((Set<T>)obj).add(value);
            localCache.get(cacheName).put(filed, obj);
        } else{
            Set<T> cacheSet = Sets.newHashSet();
            cacheSet.add(value);
            localCache.get(cacheName).put(filed, cacheSet);
        }
    }

    /**
     * 将数据放到map集合本地缓存
     * @param cacheName  缓存名
     * @param filed  缓存filed
     * @param value  缓存value
     */
    public <T> void putMapValueToCache(String cacheName, String filed, T value){
        ConcurrentMap<String,Object> concurrentMap  = localCache.get(cacheName);
        Object obj = concurrentMap.get(filed);
        if(ObjectUtil.isNotNull(obj)){
            ((Map<String,T>)obj).put(filed, value);
            localCache.get(cacheName).put(filed, obj);
        } else{
            Map<String, T> cacheMap = Maps.newConcurrentMap();
            cacheMap.put(filed, value);
            localCache.get(cacheName).put(filed, cacheMap);
        }
    }
}
