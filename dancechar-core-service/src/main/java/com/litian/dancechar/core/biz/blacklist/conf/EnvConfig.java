package com.litian.dancechar.core.biz.blacklist.conf;

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
public class EnvConfig {

    @Value("${env.dev:sit}")
    private String envDev;
}
