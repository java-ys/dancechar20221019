package com.litian.dancechar.base.biz.student.dao.inf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.base.biz.student.dao.entity.StudentDO;
import com.litian.dancechar.base.biz.student.dto.StudentReqDTO;
import com.litian.dancechar.base.biz.student.dto.StudentRespDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 学生Dao
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Mapper
public interface StudentDao extends BaseMapper<StudentDO> {

    List<StudentRespDTO> findList(StudentReqDTO req);
}