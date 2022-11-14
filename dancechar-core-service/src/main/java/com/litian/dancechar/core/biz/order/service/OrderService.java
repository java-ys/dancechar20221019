package com.litian.dancechar.core.biz.order.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.order.dao.entity.OrderInfoDO;
import com.litian.dancechar.core.biz.order.dao.inf.OrderInfoDao;
import com.litian.dancechar.core.biz.order.delaymsg.PayOrderCancelWithJDKListener;
import com.litian.dancechar.core.biz.order.dto.*;
import com.litian.dancechar.core.biz.order.enums.OrderStatusEnum;
import com.litian.dancechar.core.common.constants.CommConstants;
import com.litian.dancechar.core.feign.idgen.client.IdGenClient;
import com.litian.dancechar.core.feign.idgen.dto.IdGenReqDTO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import com.litian.dancechar.framework.delaymsg.jdk.JDKDelayQueue;
import com.litian.dancechar.framework.kafka.util.KafkaProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * 订单信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class OrderService extends ServiceImpl<OrderInfoDao, OrderInfoDO> {
    @Resource
    private OrderInfoDao orderInfoDao;
    @Resource
    private IdGenClient idGenClient;
    @Resource
    private KafkaProducerUtil kafkaProducerUtil;


    // @Resource
    // private RedisRedissonDelayQueue redisRedissonDelayQueue;
    //@Resource
    //private RedisZSetDelayQueue redisZSetDelayQueue;

    /**
     * 功能: 分页查询订单列表
     */
    public RespResult<PageWrapperDTO<OrderRespDTO>> listPaged(OrderReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<OrderRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(orderInfoDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询订单信息
     */
    public OrderInfoDO findById(String id) {
        return orderInfoDao.selectById(id);
    }

    /**
     * 功能：查询订单列表
     */
    public List<OrderRespDTO> findList(OrderReqDTO req) {
        return orderInfoDao.findList(req);
    }

    /**
     * 功能：创建订单
     */
    public RespResult<String> createOrder(OrderSaveDTO orderSaveDTO) {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        orderInfoDO.setOrderTime(new Date());
        RespResult<String> orderR = idGenClient.genId(IdGenReqDTO.builder().module("order").build());
        if(orderR.isOk()){
            orderInfoDO.setOrderNo(orderR.getData());
        }
        orderInfoDO.setStatus(OrderStatusEnum.CREATED.getCode());
        DCBeanUtil.copyNotNull(orderInfoDO, orderSaveDTO);
        orderInfoDO.setCreateUser(orderInfoDO.getMobile());
        this.save(orderInfoDO);
        // 发送订单信息
        kafkaProducerUtil.sendMessage(CommConstants.KafkaTopic.TOPIC_ORDER_INFO, JSONUtil.toJsonStr(orderInfoDO));
        // 添加延时支付取消订单任务
        JDKDelayQueue.addDelayTaskWithSeconds(orderInfoDO, 2, PayOrderCancelWithJDKListener.class);
        //PayOrderCancelWithNettyListener timerTask =new PayOrderCancelWithNettyListener();
        //timerTask.buildData(orderInfoDO);
        //NettyDelayQueue.addDelayTaskWithSeconds(timerTask, 2);
        //redisZSetDelayQueue.addDelayTaskWithMinutes(JSONUtil.toJsonStr(orderInfoDO), 2, PayOrderCancelWithRedisZSetListener.class);
        // redisRedissonDelayQueue.addDelayTaskWithMinutes(orderInfoDO, 2, PayDelayCancelWithRedissonListener.class);

        return RespResult.success(orderInfoDO.getOrderNo());
    }

    /**
     * 功能：取消订单
     */
    public RespResult<Void> cancelOrder(OrderCancelDTO orderCancelDTO) {
        List<OrderInfoDO> orderList = orderInfoDao.findByMobileAndOrderNo(orderCancelDTO.getMobile(),
                orderCancelDTO.getOrderNo());
        if(CollUtil.isEmpty(orderList)){
            return RespResult.error(String.format("取消订单失败,订单%s不存在", orderCancelDTO.getOrderNo()));
        }
        OrderInfoDO orderInfoDO = orderList.get(0);
        if(!OrderStatusEnum.CREATED.getCode().equals(orderInfoDO.getStatus())){
            return RespResult.error("只能取消已下单状态的订单");
        }
        orderInfoDO.setCancelOrderTime(DateUtil.offsetMinute(orderInfoDO.getOrderTime(), 1));
        orderInfoDO.setStatus(OrderStatusEnum.CANCEL.getCode());
        orderInfoDO.setUpdateUser(orderInfoDO.getMobile());
        this.updateById(orderInfoDO);
        return RespResult.success();
    }

    /**
     * 功能：支付订单
     */
    public RespResult<Void> payOrder(OrderPayDTO orderPayDTO) {
        List<OrderInfoDO> orderList = orderInfoDao.findByMobileAndOrderNo(orderPayDTO.getMobile(),
                orderPayDTO.getOrderNo());
        if(CollUtil.isEmpty(orderList)){
            return RespResult.error(String.format("支付订单失败,订单%s不存在", orderPayDTO.getOrderNo()));
        }
        OrderInfoDO orderInfoDO = orderList.get(0);
        if(!OrderStatusEnum.CREATED.getCode().equals(orderInfoDO.getStatus())){
            return RespResult.error("只能支付已下单状态的订单");
        }
        if(DateUtil.compare(new Date(), DateUtil.offsetMinute(orderInfoDO.getOrderTime(), 1)) > 0){
            return RespResult.error("订单已经超时，无法支付");
        }
        orderInfoDO.setPayTime(new Date());
        orderInfoDO.setStatus(OrderStatusEnum.PAYED.getCode());
        orderInfoDO.setUpdateUser(orderInfoDO.getMobile());
        this.updateById(orderInfoDO);
        return RespResult.success();
    }

    /**
     * 功能：job处理超时的订单
     */
    public void cancelOrderForJob(){
        OrderReqDTO reqDTO = new OrderReqDTO();
        reqDTO.setStatus(OrderStatusEnum.CREATED.getCode());
        reqDTO.setIsCancel(1);
        reqDTO.setLimitRecords(100);
        List<OrderRespDTO> orderList =  this.findList(reqDTO);
        if(CollUtil.isEmpty(orderList)){
            log.warn("没有自动取消的订单!时间:{}", LocalDateTime.now());
            return;
        }
        orderList.parallelStream().forEach(order->{
            OrderCancelDTO orderCancelDTO = new OrderCancelDTO();
            orderCancelDTO.setOrderNo(order.getOrderNo());
            orderCancelDTO.setMobile(order.getMobile());
            this.cancelOrder(orderCancelDTO);
        });
        log.info("自动取消订单完成！共处理{}单，时间:{}", orderList.size(), LocalDateTime.now());
    }
}