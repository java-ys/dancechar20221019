package com.litian.dancechar.core.feign.base.encrypt.dto;

import lombok.Data;

import java.util.List;

/**
 * 加解密DTO对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
public class EncryptDTO {

    /**
     * 调用来源
     */
    private String system;

    /**
     * 加密字段或解密字段
     */
    private String field;

    /**
     * 批量加密字段或解密字段
     */
    private List<String> fieldList;
}
