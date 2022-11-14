package com.litian.dancechar.core.biz.blacklist.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.core.biz.blacklist.dao.entity.SysBlackListDO;
import com.litian.dancechar.core.biz.blacklist.dto.SysBlackListReqDTO;
import com.litian.dancechar.core.biz.blacklist.dto.SysBlackListRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 黑名单请求Dao
 *
 * @author tojson
 * @date 2021/6/19 11:17
 */
@Mapper
public interface SysBlackListDao extends BaseMapper<SysBlackListDO> {

    List<SysBlackListRespDTO> findList(SysBlackListReqDTO req);
}