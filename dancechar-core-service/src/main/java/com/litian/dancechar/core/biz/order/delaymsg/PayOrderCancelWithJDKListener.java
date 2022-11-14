package com.litian.dancechar.core.biz.order.delaymsg;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.core.biz.order.dao.entity.OrderInfoDO;
import com.litian.dancechar.core.biz.order.dto.OrderCancelDTO;
import com.litian.dancechar.core.biz.order.service.OrderService;
import com.litian.dancechar.framework.delaymsg.jdk.JDKDelayedQueueListener;
import com.litian.dancechar.framework.delaymsg.redis.zset.RedisZSetDelayedQueueListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 延时支付取消订单监听
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Slf4j
@Component
public class PayOrderCancelWithJDKListener implements JDKDelayedQueueListener<OrderInfoDO> {
    @Resource
    private OrderService orderService;

    @Override
    public void execute(OrderInfoDO orderInfoDO) {
        try{
            OrderCancelDTO orderCancelDTO = new OrderCancelDTO();
            orderCancelDTO.setOrderNo(orderInfoDO.getOrderNo());
            orderCancelDTO.setMobile(orderInfoDO.getMobile());
            orderService.cancelOrder(orderCancelDTO);
            log.info("延时支付取消订单成功!data:{}", orderCancelDTO);
        }catch (Exception e){
            log.error("延时支付取消订单失败！errMsg:{}", e.getMessage(), e);
        }
    }
}
