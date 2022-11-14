package com.litian.dancechar.idgenerator.core.engine.init;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * Id生成配置刷新任务
 *
 * @author tojson
 * @date 2022/8/21 23:25
 */
@Slf4j
@Configuration
@EnableScheduling
public class IdGenConfigTask {

    @Resource
    private InitIdGenData initIdGenData;

    @Scheduled(cron = "0/5 * * * * ?")
    public void configureTasks() {
        initIdGenData.initData();
        log.info("完成id配置数据刷新！时间:{}", LocalDateTime.now());
    }
}
