package com.litian.dancechar.idgenerator.core.engine.algorithm;


/**
 * Id生成接口
 *
 * @author tojson
 * @date 2022/8/15 21:13
 */
public interface IAlgorithm {

    /**
     * 生成Id
     * @param prefix  前缀
     * @param module  模块
     * @return 返回生成的Id
     */
    String gen(String prefix , String module);
}
