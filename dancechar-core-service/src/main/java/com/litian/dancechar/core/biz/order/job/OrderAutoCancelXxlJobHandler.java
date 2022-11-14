package com.litian.dancechar.core.biz.order.job;

import com.litian.dancechar.core.biz.order.service.OrderService;
import com.litian.dancechar.framework.cache.redis.distributelock.core.DistributeLockHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 订单自动取消job(基于xxl job)
 *
 * @author tojson
 * @date 2022/8/21 23:25
 */
// @Component
@Slf4j
public class OrderAutoCancelXxlJobHandler {
    @Resource
    private OrderService orderService;
    @Resource
    private DistributeLockHelper distributeLockHelper;

    private static final String ORDER_AUTO_CANCEL_LOCK = "orderAutoCancelLock";

    @XxlJob(value = "orderAutoCancelXxlJobHandler")
    public void execute() throws Exception {
        if(!distributeLockHelper.tryLock(ORDER_AUTO_CANCEL_LOCK, TimeUnit.MINUTES, 1)){
            return;
        }
        try{
            orderService.cancelOrderForJob();
        } catch (Exception e){
            log.error("自动取消订单失败！errMsg：{}", e.getMessage(), e);
        } finally {
            distributeLockHelper.unlock(ORDER_AUTO_CANCEL_LOCK);
        }
    }
}
