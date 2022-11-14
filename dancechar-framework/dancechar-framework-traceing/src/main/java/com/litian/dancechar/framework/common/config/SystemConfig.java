package com.litian.dancechar.framework.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 系统配置
 *
 * @author tojson
 * @date 2021/6/14 22:28
 */
@Configuration
@RefreshScope
@Data
public class SystemConfig {

    @Value("${spring.profiles.active:dev}")
    private String env;
}
