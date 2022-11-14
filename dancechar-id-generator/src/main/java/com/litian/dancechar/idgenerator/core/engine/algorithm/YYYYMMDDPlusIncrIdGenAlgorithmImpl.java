package com.litian.dancechar.idgenerator.core.engine.algorithm;

import com.litian.dancechar.idgenerator.core.engine.annotation.Algorithm;
import com.litian.dancechar.idgenerator.core.engine.constants.AlgorithmKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 中间日期yyyyMMdd+自增生成算法（类似：A20220918100003）,适合并发不高的场景
 *
 * @author tojson
 * @date 2022/8/15 21:13
 */
@Component
@Slf4j
@Algorithm(value = AlgorithmKeyConstants.YYYYMMDD_PLUS_INCR_ALGORITHM)
public  class YYYYMMDDPlusIncrIdGenAlgorithmImpl extends AbstractDatePlusIncrIdGenAlgorithm{
    public String formatDate(){
        return "yyyyMMdd";
    }

    public int formatEndLength(){
        return 5;
    }
}
