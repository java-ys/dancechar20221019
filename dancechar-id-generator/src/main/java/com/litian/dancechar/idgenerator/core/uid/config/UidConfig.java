package com.litian.dancechar.idgenerator.core.uid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * uid配置
 *
 * @author tojson
 * @date 2022/8/18 23:13
 */
@Configuration
@ImportResource(locations = {"classpath:uid/cached-uid-spring.xml"})
public class UidConfig {

}