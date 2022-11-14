package com.litian.dancechar.framework.common.mybatis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置中心的参数
 *
 * @author tojson
 * @date 2021/6/14 22:17
 */
@Configuration
@Data
@RefreshScope
public class MybatisConfig {
    /**
     * 是否启用mysql限制数量
     */
    @Value("${mybatis.query.max.enable:false}")
    private Boolean queryMaxEnable;

    /**
     * 限制最大的数量
     */
    @Value("${mybatis.query.max.records: 5000}")
    private Integer queryMaxRecords;
}