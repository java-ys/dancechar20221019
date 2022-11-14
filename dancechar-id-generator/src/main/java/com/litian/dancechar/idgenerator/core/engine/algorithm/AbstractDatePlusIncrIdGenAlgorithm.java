package com.litian.dancechar.idgenerator.core.engine.algorithm;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.idgenerator.core.snowflake.SnowflakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 中间日期+自增生成算法(这里采用模版模式，定义生成步骤)
 *
 * @author tojson
 * @date 2022/8/15 21:13
 */
@Component
@Slf4j
public abstract class AbstractDatePlusIncrIdGenAlgorithm implements IAlgorithm {
    @Resource
    private RedisHelper redisHelper;

    /**
     * 使用redis生成Id（这里注意，如果生成失败，采用雪花算法兜底）
     * @param prefix  前缀
     * @param module  模块
     * @return 返回生成的Id
     */
    public String gen(String prefix, String module) {
        String key = "idGen:" +module;
        String date = DateUtil.format(new Date(), formatDate());
        int endLength = formatEndLength();
        long incrByLong = 0L;
        try{
            incrByLong = redisHelper.incrByLong(key, 1L);
        } catch (Exception e){
            log.error("使用redis生成Id出现异常！errMsg：{}", e.getMessage() ,e);
            incrByLong = SnowflakeUtil.generateId();
        }
        if(StrUtil.isNotEmpty(prefix)){
            return prefix + date + formatSeqToFixedLength(incrByLong, endLength);
        } else{
            return date + formatSeqToFixedLength(incrByLong, endLength);
        }
    }

    protected  String formatSeqToFixedLength(long seq, int length){
        String seqStr = Convert.toStr(seq);
        int len = seqStr.length();
        return len >= length ? seqStr : String.format("%0" + length + "d", seq);
    }

    abstract String formatDate();

    abstract int formatEndLength();
}
