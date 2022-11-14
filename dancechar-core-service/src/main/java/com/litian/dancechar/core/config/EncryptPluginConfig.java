package com.litian.dancechar.core.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.litian.dancechar.framework.encrypt.interceptor.EncryptInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加解密拦截器配置
 *
 * @author tojson
 * @date 2022/8/28 23:25
 */
@Configuration
public class EncryptPluginConfig {

    @Bean
    ConfigurationCustomizer configurationCustomizer() throws Exception {
        EncryptInterceptor encryptInterceptor = new EncryptInterceptor();
        return (configuration) -> {
            configuration.addInterceptor(encryptInterceptor);
        };
    }
}
