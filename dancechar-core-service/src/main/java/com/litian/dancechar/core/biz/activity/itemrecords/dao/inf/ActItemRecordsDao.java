package com.litian.dancechar.core.biz.activity.itemrecords.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.core.biz.activity.itemrecords.dao.entity.ActItemRecordsDO;
import com.litian.dancechar.core.biz.activity.itemrecords.dto.ActItemRecordReqDTO;
import com.litian.dancechar.core.biz.activity.itemrecords.dto.ActItemRecordsRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 活动领取信息Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface ActItemRecordsDao extends BaseMapper<ActItemRecordsDO> {

    List<ActItemRecordsRespDTO> findList(ActItemRecordReqDTO req);
}