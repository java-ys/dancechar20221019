package com.litian.dancechar.idgenerator.core.engine.algorithm;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.idgenerator.core.engine.annotation.Algorithm;
import com.litian.dancechar.idgenerator.core.engine.constants.AlgorithmKeyConstants;
import com.litian.dancechar.idgenerator.core.snowflake.SnowflakeUtil;
import com.litian.dancechar.idgenerator.core.uid.UidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * uid生成算法(适合高并发请求)
 *
 * @author tojson
 * @date 2022/8/15 21:13
 */
@Slf4j
@Component
@Algorithm(value = AlgorithmKeyConstants.UID_ALGORITHM)
public class UidGenAlgorithmImpl implements IAlgorithm {
    @Resource(name = "cachedUidGenerator")
    private UidGenerator uidGenerator;

    /**
     * 使用uid生成Id（这里注意，如果生成失败，采用雪花算法兜底）
     * @param prefix  前缀
     * @param module  模块
     * @return 返回生成的Id
     */
    @Override
    public String gen(String prefix, String module) {
        String result = "";
        long uid = 0L;
        try{
            uid = uidGenerator.getUID();
        }catch (Exception e){
            log.error("使用uid生成Id!errMsg:{}", e.getMessage(), e);
            uid = SnowflakeUtil.generateId();
        }
        if(StrUtil.isNotEmpty(prefix)){
            result = prefix + uid;
        }else{
            result = Convert.toStr(uid);
        }
        return result;
    }
}
