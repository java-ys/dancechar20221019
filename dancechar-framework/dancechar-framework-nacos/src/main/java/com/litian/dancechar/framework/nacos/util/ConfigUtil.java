package com.litian.dancechar.framework.nacos.util;

import cn.hutool.core.convert.Convert;
import com.litian.dancechar.framework.nacos.core.ConfigListener;

public class ConfigUtil {

    public String getByKeyFromBaseModule(String key){
        return Convert.toStr(ConfigListener.baseConfigMap.get(key));
    }
}
