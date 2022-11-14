package com.litian.dancechar.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 核心服务启动类
 *
 * @author tojson
 * @date 2021/6/19 17:51
 */
@SpringBootApplication(scanBasePackages = {"com.litian.dancechar"})
@EnableFeignClients(basePackages = "com.litian.dancechar")
@EnableDiscoveryClient
@EnableAsync
@Slf4j
public class CoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreServiceApplication.class, args);
        log.warn("dance char核心服务启动成功......");
    }
}