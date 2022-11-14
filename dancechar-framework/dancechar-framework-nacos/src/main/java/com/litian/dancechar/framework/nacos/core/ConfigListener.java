package com.litian.dancechar.framework.nacos.core;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class ConfigListener {
    /**base模块相关配置*/
    public static Map<String, String> baseConfigMap = Maps.newConcurrentMap();
    @Value("${spring.cloud.nacos.config.server-addr:}")
    private String serverAddr;
    @Value("${spring.cloud.nacos.config.namespace:}")
    private String namespace;

    @Resource
    private NacosConfigProperties configs;
    @Resource
    private ApplicationContext applicationContext;

    /**core模块相关配置*/
    public static Map<String, String> coreConfigMap = Maps.newConcurrentMap();

    // @PostConstruct
    public void init(){
        try{
            Properties properties = new Properties();
            properties.put("serverAddr", serverAddr.split(":")[0]);
            if (StringUtils.isNotBlank(namespace)) {
                properties.put("namespace", namespace);
            }
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            // 处理每个配置文件
            for (NacosConfigProperties.Config config : configs.getExtConfig()) {
                String dataId = config.getDataId();
                String group = config.getGroup();
                configService.addListener(dataId, group, new Listener() {
                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        log.info("configInfo=====", configInfo);
                        Properties proper = new Properties();
                        try {
                            proper.load(new StringReader(configInfo));
                        } catch (IOException e) {
                            log.error(e.getMessage(),e);
                        }
                        Enumeration enumeration = proper.propertyNames();
                        while (enumeration.hasMoreElements()) {
                            String strKey = (String) enumeration.nextElement();
                            baseConfigMap.put(strKey, proper.getProperty(strKey));
                        }
                    }
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }
                });
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
