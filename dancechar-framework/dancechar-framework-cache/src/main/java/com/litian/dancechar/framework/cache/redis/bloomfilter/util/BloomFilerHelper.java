package com.litian.dancechar.framework.cache.redis.bloomfilter.util;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 *
 * Bloom filter过滤器帮助类
 *
 * @author tojson
 * @date 2022/10/15 23:53
 */
public class BloomFilerHelper {

    @Resource
    private RBloomFilter<String> bloomFilter;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 创建布隆过滤器
     *
     * @param filterName         过滤器名称
     * @param expectedInsertions 预测插入数量
     * @param falsePositiveRate  误判率
     * @return RBloomFilter<T>
     */
    public <T> RBloomFilter<T> create(String filterName, long expectedInsertions, double falsePositiveRate) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        bloomFilter.tryInit(expectedInsertions, falsePositiveRate);
        return bloomFilter;
    }

    /**
     * 创建布隆过滤器
     *
     * @param filterName         过滤器名称
     * @param expectedInsertions 预测插入数量
     * @param falsePositiveRate  误判率
     * @param timeOut            过期时间
     * @param timeUnit           单位
     * @return RBloomFilter<T>
     */
    public <T> RBloomFilter<T> create(String filterName, long expectedInsertions, double falsePositiveRate,
                                      long timeOut, TimeUnit timeUnit) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        bloomFilter.tryInit(expectedInsertions, falsePositiveRate);
        expire(timeOut, timeUnit);
        return bloomFilter;
    }

    public RBloomFilter<String> getBloomFilter(){
        return bloomFilter;
    }

    /**
     * 设置过期时间
     */
    public void expire(long timeOut, TimeUnit timeUnit){
        bloomFilter.expire(timeOut, timeUnit);
    }

    /**
     * 添加元素
     */
    public boolean add(String element){
        return bloomFilter.add(element);
    }

    /**
     * 是否包含元素
     */
    public boolean contains(String element){
        return bloomFilter.contains(element);
    }

    /**
     * 不包含元素
     */
    public boolean notContains(String element){
        return !bloomFilter.contains(element);
    }

    /**
     * 删除元素
     */
    public boolean delete(){
        return bloomFilter.delete();
    }
}
