package com.litian.dancechar.core.biz.activity.info.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 活动结束对象
 *
 * @author tojson
 * @date 2022/9/6 11:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActInfoEndDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
}