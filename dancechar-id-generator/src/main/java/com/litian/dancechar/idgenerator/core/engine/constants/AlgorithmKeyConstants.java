package com.litian.dancechar.idgenerator.core.engine.constants;

/**
 * 算法key常量类
 *
 * @author tojson
 * @date 2021/6/21 21:25
 */
public interface AlgorithmKeyConstants {

    /**
     * UUID算法
     */
    String UUID = "uuid";

    /**
     * 雪花生成算法
     */
    String SNOWFLAKE_ALGORITHM = "snowflake";

    /**
     * 时间YYYYMMDDHHmmss+自增生成算法
     */
    String YYYYMMDD_HHMMSS_PLUS_INCR_ALGORITHM = "yyyymmddhhmmssplusincr";

    /**
     * 时间YYYYMMDD+自增生成算法
     */
    String YYYYMMDD_PLUS_INCR_ALGORITHM = "yyyymmddplusincr";

    /**
     * uid生成算法
     */
    String UID_ALGORITHM = "uid";
}
