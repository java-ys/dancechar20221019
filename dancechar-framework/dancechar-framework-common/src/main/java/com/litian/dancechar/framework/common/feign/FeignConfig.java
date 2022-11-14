package com.litian.dancechar.framework.common.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign 全局拦截器，转发参数到下一个服务
 *
 * @author tojson
 * @date 2022/6/ 22:13
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}