package com.litian.dancechar.framework.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求的分页DTO对象
 *
 * @author tojson
 * @date 2021/6/14 20:43
 */
@Data
public class PageReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 排序字段名
     */
    private String sort;

    /**
     * 排序順序
     */
    private String order;
}
