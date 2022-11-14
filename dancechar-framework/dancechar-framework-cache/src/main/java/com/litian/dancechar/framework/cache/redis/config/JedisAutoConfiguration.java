package com.litian.dancechar.framework.cache.redis.config;

import com.google.common.collect.Lists;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.cache.redis.common.RedisTypeEnum;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 *
 * jedis初始化相关配置
 *
 * @author tojson
 * @date 2022/01/18 17:53
 */
@EnableConfigurationProperties({JedisProperties.class})
public class JedisAutoConfiguration{
    @Resource
    private JedisProperties jedisProperties;

    @Primary
    @Bean
    public RedisProperties redisProperties(){
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setPassword(jedisProperties.getPassword());
        redisProperties.setTimeout(Duration.ofMillis(jedisProperties.getConnectionTimeOut()));
        if(RedisTypeEnum.CLUSTER.name().equalsIgnoreCase(jedisProperties.getType())){
            RedisProperties.Cluster cluster = new RedisProperties.Cluster();
            cluster.setMaxRedirects(5);
            String[] redisNodeString = jedisProperties.getSevers().split(",");
            List<String> redisNodes = Lists.newArrayList();
            Arrays.stream(redisNodeString).forEach(node->{
                redisNodes.add(node);
            });
            cluster.setNodes(redisNodes);
            redisProperties.setCluster(cluster);
        }else if(RedisTypeEnum.SENTINEL.name().equalsIgnoreCase(jedisProperties.getType())){

        }else{
            String[] ipPortPair = jedisProperties.getSevers().split(":");
            if(ipPortPair != null && ipPortPair.length > 1){
                redisProperties.setHost(ipPortPair[0]);
                redisProperties.setPort(Integer.parseInt(ipPortPair[1]));
            } else{
                redisProperties.setHost(jedisProperties.getSevers());
                redisProperties.setPort(80);
            }
        }
        redisProperties.getJedis().setPool(initRedisPool());
        return redisProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisHelper redisUtil(StringRedisTemplate stringRedisTemplate, RedissonClient redissonClient){
        return new RedisHelper(stringRedisTemplate, redissonClient);
    }

    private RedisProperties.Pool initRedisPool(){
        RedisProperties.Pool pool = new RedisProperties.Pool();
        pool.setMaxIdle(jedisProperties.getMaxIdle());
        pool.setMinIdle(jedisProperties.getMinIdle());
        pool.setMaxActive(jedisProperties.getMaxActive());
        pool.setMaxWait(Duration.ofMillis(jedisProperties.getMaxWait()));
        return pool;
    }
}
