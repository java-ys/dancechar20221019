package com.litian.dancechar.core.biz.activity.info.exception;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.litian.dancechar.core.biz.activity.info.dto.ActBaseInfoDTO;
import com.litian.dancechar.core.biz.activity.info.dto.ActInfoQueryDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.base.RespResultCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 活动sentinel限流或熔断异常逻辑处理
 *
 * @author tojson
 * @date 2022/7/9 11:26
 */
@Slf4j
public class ActSentinelBlockExceptionHandler {

    /**
     * 根据活动code-查询活动信息--限流或熔断兜底处理
     */
    public static RespResult<ActBaseInfoDTO> getActInfoByCodeBlockHandler(ActInfoQueryDTO actInfoQueryDTO,
                                                                   BlockException ex) {
        if(ex.getRule() instanceof FlowRule){
            log.error("热点参数:{}被限流！errMsg:{}", JSONUtil.toJsonStr(actInfoQueryDTO), ex.getMessage() ,ex);
        } else if(ex.getRule() instanceof DegradeRule){
            log.error("热点参数:{}被降级！errMsg:{}", JSONUtil.toJsonStr(actInfoQueryDTO), ex.getMessage() ,ex);
        }
        return RespResult.error(RespResultCode.REPEATED_BUSY);
    }
}
