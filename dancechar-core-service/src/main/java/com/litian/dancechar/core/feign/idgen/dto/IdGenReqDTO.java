package com.litian.dancechar.core.feign.idgen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * id生成请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class IdGenReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 业务模块
     */
    private String module;

    /**
     * 生成Id的大小
     */
    private int size;
}