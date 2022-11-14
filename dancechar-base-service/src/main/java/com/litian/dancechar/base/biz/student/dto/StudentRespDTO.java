package com.litian.dancechar.base.biz.student.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 学生返回對象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StudentRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 工号
     */
    private String no;

    /**
     * 姓名
     */
    private String name;

}