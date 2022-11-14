package com.litian.dancechar.idgenerator.common.constants;

/**
 * redis常量类
 *
 * @author tojson
 * @date 2022/7/28 10:33
 */
public class RedisKeyConstants {

    /**
     * IdGen生成
     */
    public static class IdGen{
        /**
         * id
         */
        public static  final String ID_GEN_KEY = "s:id:gen:%s";

        /**
         * 默认过期时间
         */
        public static final long DEFAULT_EXPIRE_TIME = 60 * 60 * 24;
    }


}
