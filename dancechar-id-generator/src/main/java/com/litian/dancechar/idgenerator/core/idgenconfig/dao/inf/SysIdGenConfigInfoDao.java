package com.litian.dancechar.idgenerator.core.idgenconfig.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.idgenerator.core.idgenconfig.dao.entity.SysIdGenConfigInfoDO;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoReqDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * id生成配置信息Dao
 *
 * @author tojson
 * @date 2022/9/11 6:30
 */
@Mapper
public interface SysIdGenConfigInfoDao extends BaseMapper<SysIdGenConfigInfoDO> {

    List<SysIdGenConfigInfoRespDTO> findList(SysIdGenConfigInfoReqDTO req);
}