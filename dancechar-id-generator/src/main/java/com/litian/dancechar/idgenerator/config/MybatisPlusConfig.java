package com.litian.dancechar.idgenerator.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus配置
 *
 * @author tojson
 * @date 2021/6/19 21:13
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public ConfigurationCustomizerImpl mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizerImpl();
    }

    class ConfigurationCustomizerImpl implements ConfigurationCustomizer {
        @Override
        public void customize(MybatisConfiguration configuration) {
            configuration.addInterceptor(new com.github.pagehelper.PageInterceptor());
        }
    }
}