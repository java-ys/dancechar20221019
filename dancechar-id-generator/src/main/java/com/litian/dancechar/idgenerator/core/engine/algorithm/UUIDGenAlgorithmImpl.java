package com.litian.dancechar.idgenerator.core.engine.algorithm;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.idgenerator.core.engine.annotation.Algorithm;
import com.litian.dancechar.idgenerator.core.engine.constants.AlgorithmKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * uuid生成算法
 *
 * @author tojson
 * @date 2022/8/15 21:13
 */
@Slf4j
@Component
@Algorithm(value = AlgorithmKeyConstants.UUID)
public class UUIDGenAlgorithmImpl implements IAlgorithm {

    /**
     * 使用uuid生成Id
     * @param prefix  前缀
     * @param module  模块
     * @return 返回生成的Id
     */
    @Override
    public String gen(String prefix, String module) {
        String uuid =  UUID.randomUUID().toString().replaceAll("-", "");
        if(StrUtil.isNotEmpty(prefix)){
            return prefix + uuid;
        } else{
            return uuid;
        }
    }
}
