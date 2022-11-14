package com.litian.dancechar.core.biz.activity.info.eventbus;

import cn.hutool.core.util.IdUtil;
import com.litian.dancechar.core.biz.activity.info.dao.entity.ActInfoDO;
import com.litian.dancechar.framework.common.eventbus.BaseEvent;
import lombok.Builder;
import lombok.Getter;

/**
 * 活动变更事件
 *
 * @author tojson
 * @date 2022/9/18 06:04
 */
@Getter
public class ActInfoChangeEvent extends BaseEvent {
    /**
     * 活动信息
     */
    private ActInfoDO actInfoDO;

    @Builder
    public ActInfoChangeEvent(ActInfoDO actInfoDO, String eventName) {
        super(IdUtil.objectId(), eventName);
        this.actInfoDO = actInfoDO;
    }
}
