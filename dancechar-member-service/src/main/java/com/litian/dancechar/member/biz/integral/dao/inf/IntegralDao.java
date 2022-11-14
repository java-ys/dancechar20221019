package com.litian.dancechar.member.biz.integral.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.member.biz.integral.dao.entity.IntegralInfoDO;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoReqDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 会员积分Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface IntegralDao extends BaseMapper<IntegralInfoDO> {

    List<IntegralInfoRespDTO> findList(IntegralInfoReqDTO req);
}