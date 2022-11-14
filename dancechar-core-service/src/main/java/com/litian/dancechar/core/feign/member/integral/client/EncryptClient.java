package com.litian.dancechar.core.feign.member.integral.client;

import com.litian.dancechar.core.feign.member.integral.dto.EncryptDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 基础服务-加解密配置-feign
 *
 * @author tojson
 * @date 2021/6/19 18:04
 */
@FeignClient("dancechar-base-service")
public interface EncryptClient {

    @PostMapping("/sys/encrypt/encryptMobile")
    RespResult<String> encryptMobile(@RequestBody EncryptDTO req);

    @PostMapping("/sys/encrypt/decryptMobile")
    RespResult<String> decryptMobile(@RequestBody EncryptDTO req);
}
