package com.litian.dancechar.framework.cache.redis.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.common.util.DCJSONUtil;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatch;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * redis操作工具类
 *
 * @author tojson
 * @date 2022/01/21 16:20
 */
public class RedisHelper {
    private RedisTemplate redisTemplate;
    private RedissonClient redissonClient;

    public RedisHelper(RedisTemplate redisTemplate, RedissonClient redissonClient){
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }
    
    public RedisTemplate getRedisTemplate(){
        return redisTemplate;
    }

    public boolean set(final String key, Object value){
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.set(key, (value instanceof String ? value : DCJSONUtil.toJsonStr(value)));
        return true;
    }

    /**
     * 设置redis string结构键值对
     * @param key    键
     * @param value  值
     * @param expireTime 过期时间，默认为毫秒
     * @return 是否设置成功
     */
    public boolean set(final String key, Object value, long expireTime){
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.set(key, (value instanceof String ? value : DCJSONUtil.toJsonStr(value)), expireTime,
                TimeUnit.MILLISECONDS);
        return true;
    }

    public boolean set(final String key, Object value, long expireTime, TimeUnit unit){
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.set(key, (value instanceof String ? value : DCJSONUtil.toJsonStr(value)), expireTime, unit);
        return true;
    }

    @SuppressWarnings("all")
    public boolean setNx(String key, Object value){
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.setIfAbsent(key, (value instanceof String ? value : DCJSONUtil.toJsonStr(value)));
    }

    @SuppressWarnings("all")
    public boolean setNx(String key, Object value, long expireTime){
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.setIfAbsent(key, (value instanceof String ? value : DCJSONUtil.toJsonStr(value)),expireTime, TimeUnit.SECONDS);
    }

    @SuppressWarnings("all")
    public boolean setNx(String key, Object value, long expireTime, TimeUnit unit){
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.setIfAbsent(key, (value instanceof String ? value : DCJSONUtil.toJsonStr(value)),expireTime, unit);
    }

    public boolean expire(final String key, long expireTime){
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    public boolean expire(final String key, long expireTime, TimeUnit timeUnit){
        return redisTemplate.expire(key, expireTime, timeUnit);
    }

    public long getExpire(final String key){
        return redisTemplate.getExpire(key);
    }

    public long ttl(final String key){
        return  redisTemplate.getExpire(key);
    }

    public long getExpire(final String key, TimeUnit timeUnit){
        return redisTemplate.getExpire(key, timeUnit);
    }

    public boolean remove(final String... keys){
        for(String key : keys){
            remove(key);
        }
        return true;
    }

    public boolean remove(String key){
        if(exists(key)){
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    /**
     * 异步删除key
     */
    public boolean unlink(String key){
        return redisTemplate.unlink(key);
    }

    /**
     * 异步批量删除key
     */
    public boolean unlink(List<String> key){
        return redisTemplate.unlink(key) > 0;
    }

    public boolean exists(final String key){
        return redisTemplate.hasKey(key);
    }

    public <T> T getBean(final String key, Class<T> beanClass){
        return DCJSONUtil.toBean(getString(key), beanClass);
    }

    public <T> List<T> getList(final String key, Class<T> beanClass){
        return DCJSONUtil.toList(getString(key), beanClass);
    }

    public String getString(final String key){
        return get(key);
    }

    public Integer getInteger(final String key){
        return Convert.toInt(get(key));
    }

    public Long getLong(final String key){
        return Convert.toLong(get(key));
    }

    public Double getDouble(final String key){
        return Convert.toDouble(get(key));
    }

    public BigDecimal getBigDecimal(final String key){
        return Convert.toBigDecimal(get(key));
    }

    public <HV> void setMap(String key, Map<String, HV> dataMap){
        HashOperations hashOperations = redisTemplate.opsForHash();
        boolean stringValue = dataMap.values().stream().allMatch(v -> v instanceof String);
        if(stringValue){
            hashOperations.putAll(key, dataMap);
            return;
        }
        Map<String, String> dataStringMap = MapUtil.newHashMap(dataMap.size());
        dataMap.forEach((k,v) -> dataStringMap.put(k, DCJSONUtil.toJsonStr(v)));
        hashOperations.putAll(key, dataStringMap);
    }

    public <HV> void setMap(String key, Map<String, HV> dataMap, Long expireTime){
        remove(key);
        setMap(key , dataMap);
        expire(key, expireTime);
    }

    public <HV> void setMap(String key, Map<String, HV> dataMap, long expireTime, TimeUnit unit){
        remove(key);
        setMap(key , dataMap);
        expire(key, expireTime, unit);
    }

    public <HV> void addToMap(String key, Map<String, HV> dataMap, long expireTime){
        setMap(key , dataMap);
        expire(key, expireTime);
    }

    public <HV> void addToMap(String key, Map<String, HV> dataMap, long expireTime, TimeUnit unit){
        setMap(key , dataMap);
        expire(key, expireTime, unit);
    }

    public <HV> void setMap(String key, String hashKey, HV hashValue){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, hashValue instanceof String ? hashValue : DCJSONUtil.toJsonStr(hashValue));
    }

    public <HV> void setMap(String key, String hashKey, HV hashValue, long expireTime){
        setMap(key, hashKey, hashValue);
        expire(key, expireTime);
    }

    public <HV> void setMap(String key, String hashKey, HV hashValue, long expireTime, TimeUnit unit){
        setMap(key, hashKey, hashValue);
        expire(key, expireTime, unit);
    }

    public Map<String, String> getMap(String key){
        return (Map<String, String>)redisTemplate.opsForHash().entries(key);
    }

    public <HV> Map<String, HV> getMap(String key, Class<HV> tClass){
        Map<String, String> map = getMap(key);
        Map<String, HV> dataMap = MapUtil.newHashMap(map.size() *2);
        map.forEach((k,v)-> dataMap.put(k, DCJSONUtil.toBean(v, tClass)));
        return dataMap;
    }

    public String getMapValue(String key, String hashKey){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return Convert.toStr(hashOperations.get(key, hashKey));
    }

    public <HV> HV getMapValue(String key, String hashKey, Class<HV> tClass){
        String mapValue = getMapValue(key, hashKey);
        if(StrUtil.isBlank(mapValue)){
            return null;
        }
        return DCJSONUtil.toBean(mapValue, tClass);
    }

    public String getMapStringValue(String key, String hashKey){
        return getMapValue(key, hashKey);
    }

    public long getMapLongValue(String key, String hashKey){
        return Convert.toLong(getMapValue(key ,hashKey));
    }

    public List<String> getMapValueList(String key, List<String> hashKeyList){
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<String> list = hashOperations.multiGet(key, hashKeyList);
        return list.stream().filter(Objects::nonNull).map(Convert::toStr).collect(Collectors.toList());
    }

    public Map<String, String> getMapValueMap(String key, List<String> hashKeyList){
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<String> list = hashOperations.multiGet(key, hashKeyList);
        LinkedHashMap<String, String> result = new LinkedHashMap<>(hashKeyList.size());
        for(int i=0; i< hashKeyList.size();i++){
            result.put(hashKeyList.get(i), list.get(i));
        }
        return result;
    }

    public <HV> Map<String, HV> getMapValueMap(String key, List<String> hashKeyList, Class<HV> hv){
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<String> list = hashOperations.multiGet(key, hashKeyList);
        LinkedHashMap<String, HV> result = new LinkedHashMap<>(hashKeyList.size());
        for(int i=0;i< hashKeyList.size();i++){
            result.put(hashKeyList.get(i), BeanUtil.toBean(list.get(i), hv));
        }
        return result;
    }

    public List<String> getMapStringValueList(String key, List<String> hashKeyList){
        return getMapValueList(key, hashKeyList);
    }

    public <HV> List<HV> getMapBeanValueList(String key, List<String> hashKeyList, Class<HV> tClass){
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<String> list = hashOperations.multiGet(key, hashKeyList);
        return list.stream().filter(Objects::nonNull).map(s -> DCJSONUtil.toBean(s, tClass)).collect(Collectors.toList());
    }

    public List<String> getMapValueList(String key){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return (List<String>)hashOperations.values(key);
    }

    public <HV> List<HV> getMapValueList(String key, Class<HV> tClass){
        List<String> mapValueList = getMapValueList(key);
        return DCJSONUtil.toBeanList(mapValueList, tClass);
    }

    public List<String> getMapStringValueList(String key){
        HashOperations hashOperations =  redisTemplate.opsForHash();
        return Convert.toList(String.class, hashOperations.values(key));
    }

    public void delFromMap(String key, String hashKey){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hashKey);
    }

    public boolean isMapCached(String key, String hashKey){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(key, hashKey);
    }

    public long hIncrementByLong(String key, String hashKey, long longVal){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.increment(key, hashKey, longVal);
    }

    public double hIncrementByLong(String key, String hashKey, double doubleVal){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.increment(key, hashKey, doubleVal);
    }

    public String get(final String key){
        ValueOperations operations = redisTemplate.opsForValue();
        return Convert.toStr(operations.get(key));
    }

    public void rPush(String key, Object value){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPush(key, value instanceof String ? value : DCJSONUtil.toJsonStr(value));
    }

    public void rPushAll(String key, String[] value){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPushAll(key, value);
    }

    public Object rPop(String key){
        ListOperations<String,Object> list = redisTemplate.opsForList();
        return list.leftPop(key);
    }

    public void lPush(String key, Object value){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(key, value instanceof String ? value : DCJSONUtil.toJsonStr(value));
    }

    public void lPushAll(String key, String[] value){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPushAll(key, value);
    }

    public Object lPop(String key){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.rightPop(key);
    }

    public List<String> lRange(String key, long start, long end){
        ListOperations<String, String> list = redisTemplate.opsForList();
        return list.range(key, start, end);
    }

    public <T> List<T> lRange(String key, long start, long end, Class<T> tClass){
        return DCJSONUtil.toBeanList(lRange(key, start, end), tClass);
    }

    public List<String> lRange(String key){
        ListOperations<String, String> list = redisTemplate.opsForList();
        return list.range(key, 0 , -1);
    }

    public <T> List<T> lRange(String key, Class<T> tClass){
        return DCJSONUtil.toBeanList(lRange(key), tClass);
    }

    public List<String> popAndClear(String key, long start, long end){
        ListOperations<String, String> list = redisTemplate.opsForList();
        List<String> returnList = lRange(key, start, end);
        list.trim(key, end+1, -1);
        return returnList;
    }

    public <T> List<T> popAndClear(String key, long start, long end, Class<T> tClass){
        return DCJSONUtil.toBeanList(popAndClear(key, start, end),tClass);
    }

    public void trimList(String key, long start, long end){
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.trim(key,start,end);
    }

    public void trimListAll(String key){
        trimList(key, 1, 0);
    }

    public long listSize(String key){
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.size(key);
    }

    public long incrByLong(String key, long value){
        return redisTemplate.opsForValue().increment(key, value);
    }

    public double incrByDouble(String key, double value){
        return redisTemplate.opsForValue().increment(key, value);
    }

    public void setAdd(String key, Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value instanceof String ? value : DCJSONUtil.toJsonStr(value));
    }

    public Set<String> members(String key){
        SetOperations<String,String> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    public <T> Set<T>  members(String key, Class<T> tClass){
        return DCJSONUtil.toBeanSet(members(key), tClass);
    }

    public long setSize(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.size(key);
    }

    public long setRemove(String key, Object... values){
        SetOperations<String ,Object> set = redisTemplate.opsForSet();
        return set.remove(key, Arrays.stream(values).map(t -> t instanceof String ? t :
                DCJSONUtil.toJsonStr(t)).toArray());
    }

    public long zSetSize(String key){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.size(key);
    }

    public Set<String> zSetRange(String key, long start, long end){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        return zset.range(key, start, end);
    }

    public <T> Set<T> zSetRange(String key, long start, long end, Class<T> tClass){
        return DCJSONUtil.toBeanSet(zSetRange(key, start, end), tClass);
    }

    public Set<String> zSetRange(String key){
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        return zSet.range(key, 0, -1);
    }

    public <T> Set<T>  zSetRange(String key, Class<T> tClass){
        return DCJSONUtil.toBeanSet(zSetRange(key), tClass);
    }

    public Set<String> zSetRangeByScore(String key, double min ,double max){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, min, max);
    }

    public Set<String> zSetRangeByScore(String key, double min ,double max, long offset, long count){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, min, max, offset, count);
    }

    public void zSetAdd(String key, Object value, double score){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value instanceof String ?  value : DCJSONUtil.toJsonStr(value), score);
    }

    public long zSetRemove(String key, Object... values){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.remove(key, Arrays.stream(values).map(t -> t instanceof String ? t :
                DCJSONUtil.toJsonStr(t)).toArray());
    }

    public <T> Set<T> zSetRangeByScore(String key, double min ,double max, Class<T> tClass){
        return DCJSONUtil.toBeanSet(zSetRangeByScore(key, min, max), tClass);
    }

    public Set<String> zSetReverseRange(String key, long start , long end){
        ZSetOperations<String,String> zSet = redisTemplate.opsForZSet();
        return zSet.reverseRange(key, start, end);
    }

    public Set<String> zSetReverseRange(String key){
        ZSetOperations<String,String> zSet = redisTemplate.opsForZSet();
        return zSet.reverseRange(key, 0, -1);
    }

    public void executePipeLinedSetMap(String key, Map<String, String> map, long seconds){
        RBatch batch = redissonClient.createBatch(BatchOptions.defaults());
        map.forEach((k,v) ->{
            batch.getMap(key, new StringCodec()).fastPutAsync(k, v);
        });
        batch.getMap(key).expireAsync(seconds, TimeUnit.SECONDS);
        batch.executeAsync();
    }

}
