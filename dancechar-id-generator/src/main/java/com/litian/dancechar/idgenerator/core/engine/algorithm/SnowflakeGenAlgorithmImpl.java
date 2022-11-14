package com.litian.dancechar.idgenerator.core.engine.algorithm;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.idgenerator.core.engine.annotation.Algorithm;
import com.litian.dancechar.idgenerator.core.engine.constants.AlgorithmKeyConstants;
import com.litian.dancechar.idgenerator.core.snowflake.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 雪花生成算法
 *
 * @author tojson
 * @date 2022/8/15 21:13
 */
@Slf4j
@Component
@Algorithm(value = AlgorithmKeyConstants.SNOWFLAKE_ALGORITHM)
public class SnowflakeGenAlgorithmImpl implements IAlgorithm {

    /**
     * 使用雪花算法生成Id
     * @param prefix  前缀
     * @param module  模块
     * @return 返回生成的Id
     */
    @Override
    public String gen(String prefix, String module) {
        String snowflake;
        try{
            snowflake = Convert.toStr(SnowflakeUtil.generateId());
        } catch (Exception e){
            log.error("使用雪花算法生成Id出现异常！errMsg：{}", e.getMessage() ,e);
            snowflake = UUID.randomUUID().toString().replaceAll("-", "");
        }
        if(StrUtil.isNotEmpty(prefix)){
            return prefix + snowflake;
        } else{
            return snowflake;
        }
    }
}
