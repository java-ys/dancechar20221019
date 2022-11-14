package com.litian.dancechar.core.biz.order.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.core.biz.order.dao.entity.OrderInfoDO;
import com.litian.dancechar.core.biz.order.dto.OrderReqDTO;
import com.litian.dancechar.core.biz.order.dto.OrderRespDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 订单信息Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface OrderInfoDao extends BaseMapper<OrderInfoDO> {

    List<OrderRespDTO> findList(OrderReqDTO req);

    List<OrderInfoDO> findByMobileAndOrderNo(@Param("mobile")String mobile, @Param("orderNo")String orderNo);
}