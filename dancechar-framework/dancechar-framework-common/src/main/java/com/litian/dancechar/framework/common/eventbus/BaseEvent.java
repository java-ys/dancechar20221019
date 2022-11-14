package com.litian.dancechar.framework.common.eventbus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * event bus 基本事件类
 *
 * @author tojson
 * @date 2020年6月23日 下午1:45:15
 */
@Data
@AllArgsConstructor
public class BaseEvent {
    /**
     * 事件Id
     */
    public String eventId;

    /**
     * 事件名
     */
    public String eventName;
}
