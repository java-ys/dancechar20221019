package com.litian.dancechar.framework.common.httpclient;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * http连接池参数配置
 *
 * @author tojson
 * @date 2022/8/14 22:28
 */
@Configuration
@Slf4j
@RefreshScope
@Data
public class HttpPoolConfig {
    @Value("${http.pool.connect.socket.timeout:2000}")
    private Integer socketTimeOut;

    @Value("${http.pool.connect.timeout:200}")
    private Integer connectTimeOut;

    @Value("${http.pool.httpclient.max.total:1500}")
    private Integer httpclientMaxTotal;

    @Value("${thread.pool.max.per.route:400}")
    private Integer maxPerRoute;
}
