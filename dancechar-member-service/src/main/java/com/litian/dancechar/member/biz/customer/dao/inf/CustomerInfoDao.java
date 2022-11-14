package com.litian.dancechar.member.biz.customer.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.member.biz.customer.dao.entity.CustomerInfoDO;
import com.litian.dancechar.member.biz.customer.dto.CustomerInfoReqDTO;
import com.litian.dancechar.member.biz.customer.dto.CustomerInfoRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 会员信息Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface CustomerInfoDao extends BaseMapper<CustomerInfoDO> {

    List<CustomerInfoRespDTO> findList(CustomerInfoReqDTO req);
}