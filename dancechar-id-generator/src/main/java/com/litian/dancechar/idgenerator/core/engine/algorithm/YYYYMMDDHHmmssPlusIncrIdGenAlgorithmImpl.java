package com.litian.dancechar.idgenerator.core.engine.algorithm;

import com.litian.dancechar.idgenerator.core.engine.annotation.Algorithm;
import com.litian.dancechar.idgenerator.core.engine.constants.AlgorithmKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 中间日期yyyyMMddHHmmss+自增生成算法（类似：A2022091823050100003),适合并发不高的场景
 *
 * @author tojson
 * @date 2022/8/15 21:13
 */
@Slf4j
@Component
@Algorithm(value = AlgorithmKeyConstants.YYYYMMDD_HHMMSS_PLUS_INCR_ALGORITHM)
public  class YYYYMMDDHHmmssPlusIncrIdGenAlgorithmImpl extends AbstractDatePlusIncrIdGenAlgorithm{
    public String formatDate(){
        return "yyyyMMddHHmmss";
    }

    public int formatEndLength(){
        return 5;
    }
}
