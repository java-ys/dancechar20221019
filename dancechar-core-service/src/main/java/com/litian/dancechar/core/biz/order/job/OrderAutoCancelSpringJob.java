package com.litian.dancechar.core.biz.order.job;


import com.litian.dancechar.core.biz.order.service.OrderService;
import com.litian.dancechar.framework.cache.redis.distributelock.core.DistributeLockHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 订单自动取消job(基于spring)
 *
 * @author tojson
 * @date 2022/8/21 23:25
 */
@Slf4j
@Configuration
@EnableScheduling
public class OrderAutoCancelSpringJob {
    @Resource
    private OrderService orderService;
    @Resource
    private DistributeLockHelper distributeLockHelper;

    private static final String ORDER_AUTO_CANCEL_LOCK = "orderAutoCancelLock";

    @Scheduled(cron = "0 0/10 * * * ?")
    public void cancelOrder() {
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
