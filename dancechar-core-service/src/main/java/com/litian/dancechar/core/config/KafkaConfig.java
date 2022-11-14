package com.litian.dancechar.core.config;

import com.litian.dancechar.framework.kafka.util.KafkaProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kafka配置
 *
 * @author tojson
 * @date 2022/8/28 23:25
 */
@Slf4j
@Configuration
public class KafkaConfig {

    @Bean
    public KafkaProducerUtil KafkaProducerUtil() {
        return new KafkaProducerUtil();
    }
}
