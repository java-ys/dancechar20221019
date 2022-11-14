package com.litian.dancechar.idgenerator.core.engine.init;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoReqDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoRespDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.service.SysIdGenConfigInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统启动初始化ID生成数据
 *
 * @author tojson
 * @date 2022/8/15 21:13
 */
@Component
@Slf4j
public class InitIdGenData {
    @Resource
    private SysIdGenConfigInfoService sysIdGenConfigInfoService;
    public static Map<String, SysIdGenConfigInfoRespDTO> moduleToRule = Maps.newConcurrentMap();

    @PostConstruct
    public void initData(){
        SysIdGenConfigInfoReqDTO sysIdGenConfigInfoReqDTO = new SysIdGenConfigInfoReqDTO();
        List<SysIdGenConfigInfoRespDTO> configList =  sysIdGenConfigInfoService
                .findList(sysIdGenConfigInfoReqDTO);
        if(CollUtil.isNotEmpty(configList)){
            for (SysIdGenConfigInfoRespDTO sysIdGenConfigInfoRespDTO : configList){
                if(StrUtil.isEmpty(sysIdGenConfigInfoRespDTO.getModule())
                   || StrUtil.isEmpty(sysIdGenConfigInfoRespDTO.getGenRule())){
                    continue;
                }
                moduleToRule.put(sysIdGenConfigInfoRespDTO.getModule(), sysIdGenConfigInfoRespDTO);
            }
        }
    }
}
