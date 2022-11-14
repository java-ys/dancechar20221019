package com.litian.dancechar.base.biz.student.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import com.litian.dancechar.framework.encrypt.annotation.EncryptClass;
import com.litian.dancechar.framework.encrypt.annotation.EncryptField;
import com.litian.dancechar.framework.encrypt.enums.EncryptTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 学生DO
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@TableName("t_student")
@EqualsAndHashCode(callSuper = false)
@EncryptClass
public class StudentDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 工号
     */
    @EncryptField(value = EncryptTypeEnum.MOBILE)
    private String no;

    /**
     * 姓名
     */
    private String name;
}