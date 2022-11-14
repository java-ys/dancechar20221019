package com.litian.dancechar.core.feign.base.commonconfig.client;

import com.litian.dancechar.core.feign.base.commonconfig.dto.SysWhiteListReqDTO;
import com.litian.dancechar.core.feign.base.commonconfig.dto.SysWhiteListRespDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;


/**
 * 基础服务feign
 *
 * @author tojson
 * @date 2021/6/19 18:04
 */
@FeignClient("dancechar-base-service")
public interface BaseServiceClient {
    /**
     * 查询白名单列表
     */
    @PostMapping("/sys/whitelist/findList")
    RespResult<List<SysWhiteListRespDTO>> findWhiteList(@RequestBody SysWhiteListReqDTO req);
}
