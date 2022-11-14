package com.litian.dancechar.idgenerator.core.engine.api;

import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.idgenerator.core.engine.dto.IdGenReqDTO;
import com.litian.dancechar.idgenerator.core.engine.service.IdGenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 对外Id生成业务处理接口
 *
 * @author tojson
 * @date 2022/9/10 23:26
 */
@Api(tags = "对外Id生成业务处理接口")
@RestController
@Slf4j
@RequestMapping(value = "/id/gen/")
public class IdGenController {
    @Resource
    private IdGenService idGenService;

    @ApiOperation(value = "生成Id", notes = "生成Id")
    @PostMapping("genId")
    public RespResult<String> genId(@RequestBody IdGenReqDTO reqDTO) {
        if(StrUtil.isEmpty(reqDTO.getModule())){
            return RespResult.error("module不能为空");
        }
        return idGenService.genId(reqDTO.getModule());
    }

    @ApiOperation(value = "批量生成Id", notes = "批量生成Id")
    @PostMapping("batchGenIds")
    public RespResult<List<String>> batchGenIds(@RequestBody IdGenReqDTO reqDTO) {
        if(StrUtil.isEmpty(reqDTO.getModule())){
            return RespResult.error("module不能为空");
        }
        if(reqDTO.getSize() <=0){
            return RespResult.error("size必须大于0");
        }
        return idGenService.batchGenIds(reqDTO.getModule(), reqDTO.getSize());
    }
}