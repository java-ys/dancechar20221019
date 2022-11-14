package com.litian.dancechar.framework.cache.redis.common;


public enum RedisTypeEnum {
    /**
     * 单机
     */
    REDIS,
    /**
     * sentinel集群
     */
    SENTINEL,
    /**
     * cluster集群
     */
    CLUSTER;

    private RedisTypeEnum(){
    }

}
