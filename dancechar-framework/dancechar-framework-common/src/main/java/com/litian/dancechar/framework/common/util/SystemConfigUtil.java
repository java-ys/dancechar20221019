package com.litian.dancechar.framework.common.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import com.litian.dancechar.framework.common.config.SystemEnvConfig;
import org.springframework.stereotype.Component;

/**
 * 获取系统配置工具类
 *
 * @author leo
 * @date 2021/6/19 18:57
 */
@Component
public class SystemConfigUtil {
    /**
     * 获取部署环境信息
     */
    public static String getEnv() {
        if( SpringUtil.getApplicationContext() != null){
            return Convert.toStr(SpringUtil.getBean(SystemEnvConfig.class).getEnv(), "dev");
        }
        return "dev";
    }

}
