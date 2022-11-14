package com.litian.dancechar.framework.delaymsg.jdk.test;

import com.litian.dancechar.framework.delaymsg.jdk.JDKDelayQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * JDK delayqueue实现延时队列案例-入口
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Slf4j
public class JDKTestMain {

    public void createOrder() {
        JDKTestVO jdkTestVO = new JDKTestVO();
        jdkTestVO.setNo("123");
        JDKDelayQueue.addDelayTaskWithMinutes(jdkTestVO, 2, JDKTestListener.class);
    }
}