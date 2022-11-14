package com.litian.dancechar.framework.delaymsg.jdk.test;

import com.litian.dancechar.framework.delaymsg.jdk.JDKDelayedQueueListener;
import lombok.extern.slf4j.Slf4j;

/**
 *  JDK delayqueue实现延时队列案例
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Slf4j
public class JDKTestListener implements JDKDelayedQueueListener<JDKTestVO> {
    @Override
    public void execute(JDKTestVO jdkTestVO) {
        try{
            // todo
            log.info("todo----JDKTestListener");
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
