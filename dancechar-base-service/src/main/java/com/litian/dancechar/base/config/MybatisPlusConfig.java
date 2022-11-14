package com.litian.dancechar.base.config;

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

    /*
    /**
     * 分页插件，自动识别数据库类型
    //@Bean
    public PaginationInterceptor paginationInterceptor() {
        // return new PaginationInterceptor();
    }
    */

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