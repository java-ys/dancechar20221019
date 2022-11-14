package com.litian.dancechar.framework.cache.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

/**
 *
 * redisson初始化相关配置
 *
 * @author tojson
 * @date 2022/01/18 16:20
 */
@Slf4j
@Configuration
@AutoConfigureBefore(JedisAutoConfiguration.class)
public class RedissonAutoConfiguration {
    @Resource
    private JedisProperties jedisProperties;

    @Bean
    public RedissonClient createRedissonClient() {
        Config config = new Config();
        String[] ipPortPair = jedisProperties.getSevers().split(":");
        if(ipPortPair == null && ipPortPair.length == 0){
            return null;
        }
        String address = new StringBuilder("redis://").append(ipPortPair[0]).append(":")
                .append(ipPortPair[1]).toString();
        config.useSingleServer().setAddress(address);
        String password = jedisProperties.getPassword();
        if (null != password && !"".equals(password.trim())) {
            config.useSingleServer().setPassword(password);
        }
        return Redisson.create(config);
    }
}
