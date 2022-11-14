package com.litian.dancechar.framework.cache.redis.config;

import com.litian.dancechar.framework.cache.redis.common.RedisTypeEnum;
import lombok.Data;
import org.redisson.config.ReadMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.redis")
public class JedisProperties {

    /**
     * 默认连接超时时间
     */
    public static final int DEFAULT_CONNECT_TIME_OUT = 10000;

    /**
     * 默认超时时间
     */
    public static final int DEFAULT_TIME_OUT = 10000;

    /**
     * 最小连接超时时间
     */
    public static final int MIN_CONNECT_TIME_OUT = 100;

    /**
     * 默认最大空闲连接数
     */
    public static final int DEFAULT_MAX_IDLE = 1000;

    /**
     * 默认最小空闲连接数
     */
    public static final int DEFAULT_MIN_IDLE = 0;

    /**
     * 默认最大活跃连接数
     */
    public static final int DEFAULT_MAX_ACTIVE = 1400;

    /**
     * 默认最大等待时间（毫秒）
     */
    public static final int DEFAULT_MAX_WAIT = 1500;

    /**
     * 当空闲连接数大于最小空闲连接，且该连接大于时间未适用则关闭，默认未10000毫秒
     */
    public static final int DEFAULT_IDLE_CONN_TIME_OUT = 10000;

    /**
     * 默认重连次数
     */
    public static final int RETRY_ATTEMPTS = 3;

    /**
     * 默认ping时间间隔
     */
    public static final int PING_INTERVAL = 1000;

    /**
     * 多台用，隔开
     */
    private String severs;
    private String type = RedisTypeEnum.REDIS.name();
    private String password;
    private int connectionTimeOut = DEFAULT_CONNECT_TIME_OUT;
    private int timeOut = DEFAULT_TIME_OUT;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;
    private int maxActive = DEFAULT_MAX_ACTIVE;
    private int maxWait = DEFAULT_MAX_WAIT;
    private int retryAttempts = 3;
    private int idleConnTimeOut = DEFAULT_IDLE_CONN_TIME_OUT;
    private int pingInterval = PING_INTERVAL;

    private String readMode = ReadMode.SLAVE.name();
    private Master master = new Master();
    private Slave slave = new Slave();

    public static class Master{
        private int connectionTimeOut = DEFAULT_CONNECT_TIME_OUT;
        private int timeOut = DEFAULT_TIME_OUT;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;
        private int maxActive = DEFAULT_MAX_ACTIVE;
        private int maxWait = DEFAULT_MAX_WAIT;
    }

    public static class Slave{
        private int connectionTimeOut = DEFAULT_CONNECT_TIME_OUT;
        private int timeOut = DEFAULT_TIME_OUT;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;
        private int maxActive = DEFAULT_MAX_ACTIVE;
        private int maxWait = DEFAULT_MAX_WAIT;
    }

    public int getConnectionTimeOut(){
        if(connectionTimeOut < MIN_CONNECT_TIME_OUT){
            return DEFAULT_CONNECT_TIME_OUT;
        }
        return connectionTimeOut;
    }
}
