package com.litian.dancechar.idgenerator.core.engine.init;

import com.litian.dancechar.idgenerator.core.engine.algorithm.IAlgorithm;
import com.litian.dancechar.idgenerator.core.engine.annotation.Algorithm;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * 初始化-模块算法对应关系
 *
 * @author tojson
 * @date 2022/8/22 22:10
 */
@Component
public class InitModuleAlgorithmConfig implements ApplicationContextAware {
    public static final Map<String, IAlgorithm> moduleToIdGenAlgorithmMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansWithAnnotation(Algorithm.class).
                entrySet().iterator().forEachRemaining(entrySet -> {
                    Class<IAlgorithm> algorithmHandler = (Class<IAlgorithm>) entrySet.getValue().getClass();
                    String value = algorithmHandler.getAnnotation(Algorithm.class).value();
                    // 将class加入moduleToIdGenAlgorithmMap的map中,注解的value作为key
                    moduleToIdGenAlgorithmMap.put(value, (IAlgorithm) entrySet.getValue());
                });
    }
}
