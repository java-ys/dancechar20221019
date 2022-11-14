package com.litian.dancechar.framework.delaymsg.netty.test;

import cn.hutool.core.date.DateUtil;
import com.litian.dancechar.framework.delaymsg.netty.NettyDelayQueue;

import java.util.Date;

/**
 *  netty时间轮实现延时队列案例
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
public class NettyDelayMsgTestMain {

    public static void main(String[] args) {
        NettyTestListener timerTask =new NettyTestListener();
        timerTask.buildData(TestDataObj.builder().actNo("001").actName("活动").build());
        NettyDelayQueue.addDelayTaskWithSeconds(timerTask,5);

        NettyTestListener timerTask2 =new NettyTestListener();
        timerTask2.buildData(TestDataObj.builder().actNo("002").actName("活动2").build());
        NettyDelayQueue.addDelayTaskWithMinutes(timerTask2,2);

        NettyTestListener timerTask3 =new NettyTestListener();
        timerTask3.buildData(TestDataObj.builder().actNo("003").actName("活动3").build());
        Date futureDate = DateUtil.parse("2022-09-30 21:47:00", "yyyy-MM-dd HH:mm:ss");
        NettyDelayQueue.addDelayTaskWithFutureTime(timerTask3,futureDate);
    }
}
