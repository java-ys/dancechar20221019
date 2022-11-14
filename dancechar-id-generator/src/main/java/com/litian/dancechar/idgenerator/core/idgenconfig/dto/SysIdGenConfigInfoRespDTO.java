package com.litian.dancechar.idgenerator.core.idgenconfig.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * id生成配置信息返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysIdGenConfigInfoRespDTO extends BaseRespDTO implements Serializable {
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