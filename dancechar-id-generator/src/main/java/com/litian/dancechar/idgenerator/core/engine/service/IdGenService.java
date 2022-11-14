package com.litian.dancechar.idgenerator.core.engine.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.idgenerator.core.engine.algorithm.IAlgorithm;
import com.litian.dancechar.idgenerator.core.engine.init.InitIdGenData;
import com.litian.dancechar.idgenerator.core.engine.init.InitModuleAlgorithmConfig;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * id生成服务处理
 * @author tojson
 * @date 2021/6/19 21:13
 */
@Slf4j
@Component
public class IdGenService {

    /**
     * 获取单个ID
     * @param module 业务模块
     * @return 返回单个Id
     */
    public RespResult<String> genId(String module){
        SysIdGenConfigInfoRespDTO rule = InitIdGenData.moduleToRule.get(module);
        if(ObjectUtil.isNull(rule) || StrUtil.isEmpty(rule.getGenRule())){
            return RespResult.error("module："+ module + "没有配置生成规则");
        }
        IAlgorithm idGenAlgorithm = InitModuleAlgorithmConfig.moduleToIdGenAlgorithmMap.get(rule.getGenRule());
        if(ObjectUtil.isNull(idGenAlgorithm)){
            return RespResult.error("module："+ module + "生成规则不存在");
        }
        return RespResult.success(idGenAlgorithm.gen(rule.getPrefix(), module));
    }

    /**
     * 批量获取多个ID
     * @param module 业务模块
     * @param size  单次生成的大小
     * @return 返回多个ID列表
     */
    public RespResult<List<String>> batchGenIds(String module, int size){
        SysIdGenConfigInfoRespDTO rule = InitIdGenData.moduleToRule.get(module);
        if(ObjectUtil.isNull(rule) || StrUtil.isEmpty(rule.getGenRule())){
            return RespResult.error("module："+ module + "没有配置生成规则");
        }
        IAlgorithm idGenAlgorithm = InitModuleAlgorithmConfig.moduleToIdGenAlgorithmMap.get(rule.getGenRule());
        if(ObjectUtil.isNull(idGenAlgorithm)){
            return RespResult.error("module："+ module + "生成规则不存在");
        }
        Set<String> result = Sets.newHashSet();
        for(int i=0; i<size; i++){
            result.add(idGenAlgorithm.gen(rule.getPrefix(), module));
        }
        int reduce = size - result.size();
        if(reduce > 0){
            for(int j=0; j<reduce; j++){
                result.add(idGenAlgorithm.gen(rule.getPrefix(), module));
            }
        }
        return RespResult.success(Lists.newArrayList(result));
    }
}
