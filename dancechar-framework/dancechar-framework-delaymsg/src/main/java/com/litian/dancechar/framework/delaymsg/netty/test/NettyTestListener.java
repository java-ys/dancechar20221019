package com.litian.dancechar.framework.delaymsg.netty.test;

import cn.hutool.json.JSONUtil;
import com.litian.dancechar.framework.delaymsg.netty.TaskVO;
import com.litian.dancechar.framework.delaymsg.netty.AbstractNettyDelayedQueueListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试待执行的任务
 *
 * @author tojson
 * @date 2022/09/28 23:05
 */
@Slf4j
public  class NettyTestListener extends AbstractNettyDelayedQueueListener<TestDataObj> {
    @Override
    public void execute(TaskVO<TestDataObj> data) {
        log.info("测试NettyHashedWheelTimer,taskId:{},data:{}", data.getId(),
                JSONUtil.toJsonStr(data.getData()));
    }
}
