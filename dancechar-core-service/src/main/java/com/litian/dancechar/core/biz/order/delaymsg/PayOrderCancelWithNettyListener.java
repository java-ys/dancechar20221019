package com.litian.dancechar.core.biz.order.delaymsg;

import com.litian.dancechar.core.biz.order.dao.entity.OrderInfoDO;
import com.litian.dancechar.core.biz.order.dto.OrderCancelDTO;
import com.litian.dancechar.core.biz.order.service.OrderService;
import com.litian.dancechar.framework.common.util.SpringContextHoldUtil;
import com.litian.dancechar.framework.delaymsg.netty.AbstractNettyDelayedQueueListener;
import com.litian.dancechar.framework.delaymsg.netty.TaskVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 延时支付取消订单监听
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Slf4j
public class PayOrderCancelWithNettyListener extends AbstractNettyDelayedQueueListener<OrderInfoDO> {

    @Override
    public void execute(TaskVO<OrderInfoDO> taskVO) {
        try{
            OrderInfoDO orderInfoDO = taskVO.getData();
            OrderCancelDTO orderCancelDTO = new OrderCancelDTO();
            orderCancelDTO.setOrderNo(orderInfoDO.getOrderNo());
            orderCancelDTO.setMobile(orderInfoDO.getMobile());
            SpringContextHoldUtil.getBean(OrderService.class).cancelOrder(orderCancelDTO);
            log.info("延时支付取消订单成功!data:{}", orderCancelDTO);
        }catch (Exception e){
            log.error("延时支付取消订单失败！errMsg:{}", e.getMessage(), e);
        }
    }
}
