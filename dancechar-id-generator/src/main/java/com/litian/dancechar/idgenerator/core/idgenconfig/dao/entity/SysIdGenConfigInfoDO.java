package com.litian.dancechar.idgenerator.core.idgenconfig.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * id生成配置信息DO
 *
 * @author tojson
 * @date 2022/9/5 06:18
 */
@Data
@TableName("sys_id_gen_config_info")
@EqualsAndHashCode(callSuper = false)
public class SysIdGenConfigInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 模块
     */
    private String module;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 生成规则
     */
    private String genRule;
}