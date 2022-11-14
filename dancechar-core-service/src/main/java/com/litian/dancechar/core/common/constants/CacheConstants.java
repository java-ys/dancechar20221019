package com.litian.dancechar.core.common.constants;

/**
 * 缓存常量类
 *
 * @author tojson
 * @date 2022/7/28 10:33
 */
public class CacheConstants {

    /**
     * 活动信息
     */
    public static class ActInfo{
        /**
         * caffeine活动缓存key
         */
        public static  final String CAFFEINE_ACT_INFO = "actInfo";
        /**
         * 活动基本信息(采用string结构存储)
         */
        public static  final String ACT_BASE_INFO = "act:base:info:%s";
        /**
         * 活动规则(不与活动基本信息放一起，单独存储，采用string结构存储)
         */
        public static  final String ACT_RULE = "act:rule:%s";
        /**
         * 活动列表
         */
        public static  final String ACT_LIST = "act_list";

        /**
         * 活动列表-过期时间(7天)
         */
        public static  final long ACT_LIST_EXPIRE_TIME = 7 * 24 * 3600 * 1000L;
    }
}
