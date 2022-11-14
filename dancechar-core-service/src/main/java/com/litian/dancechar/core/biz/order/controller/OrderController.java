package com.litian.dancechar.core.biz.order.controller;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.core.biz.activity.taskconfig.dto.ActTaskConfigInfoRespDTO;
import com.litian.dancechar.core.biz.order.dto.*;
import com.litian.dancechar.core.biz.order.service.OrderService;
import com.litian.dancechar.framework.cache.redis.distributelock.annotation.Lock;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单信息业务处理
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Api(tags = "订单信息相关api")
@RestController
@Slf4j
@RequestMapping(value = "/order/")
public class OrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation(value = "分页查询订单列表", notes = "分页查询订单列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<OrderRespDTO>> listPaged(@RequestBody OrderReqDTO req) {
        return orderService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询订单信息", notes = "根据Id查询订单信息")
    @PostMapping("findById")
    public RespResult<ActTaskConfigInfoRespDTO> findById(@RequestBody OrderReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(new ActTaskConfigInfoRespDTO(),
                orderService.findById(req.getId())));
    }

    @ApiOperation(value = "创建订单信息", notes = "创建订单信息")
    @PostMapping("createOrder")
    @Lock(value = "#orderSaveDTO.mobile", lockFailMsg = "请勿重复提交", expireTime = 10000)
    public RespResult<String> createOrder(@RequestBody OrderSaveDTO orderSaveDTO) {
        log.info("创建订单信息....");
        if(StrUtil.isEmpty(orderSaveDTO.getMobile())){
            return RespResult.error("客户手机号不能为空");
        }
        return orderService.createOrder(orderSaveDTO);
    }

    @ApiOperation(value = "取消订单信息", notes = "取消订单信息")
    @PostMapping("cancelOrder")
    // @Lock(value = "#orderCancelDTO.mobile,#orderCancelDTO.orderNo", lockFailMsg = "请勿重复提交", expireTime = 3000)
    public RespResult<Void> cancelOrder(@RequestBody OrderCancelDTO orderCancelDTO) {
        log.info("取消订单信息....");
        if(StrUtil.isEmpty(orderCancelDTO.getMobile())){
            return RespResult.error("客户手机号不能为空");
        }
        if(StrUtil.isEmpty(orderCancelDTO.getOrderNo())){
            return RespResult.error("订单号不能为空");
        }
        return orderService.cancelOrder(orderCancelDTO);
    }

    @ApiOperation(value = "支付订单信息", notes = "支付订单信息")
    @PostMapping("payOrder")
    @Lock(value = "#orderPayDTO.mobile,#orderPayDTO.orderNo", lockFailMsg = "请勿重复提交", expireTime = 3000)
    public RespResult<Void> payOrder(@RequestBody OrderPayDTO orderPayDTO) {
        log.info("支付订单信息....");
        if(StrUtil.isEmpty(orderPayDTO.getMobile())){
            return RespResult.error("客户手机号不能为空");
        }
        if(StrUtil.isEmpty(orderPayDTO.getOrderNo())){
            return RespResult.error("订单号不能为空");
        }
        return orderService.payOrder(orderPayDTO);
    }
}