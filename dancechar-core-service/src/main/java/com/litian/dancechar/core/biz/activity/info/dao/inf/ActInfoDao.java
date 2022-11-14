package com.litian.dancechar.core.biz.activity.info.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.core.biz.activity.info.dto.ActInfoReqDTO;
import com.litian.dancechar.core.biz.activity.info.dto.ActInfoRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 活动信息Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface ActInfoDao extends BaseMapper<ActInfoDO> {

    List<ActInfoRespDTO> findList(ActInfoReqDTO req);
}