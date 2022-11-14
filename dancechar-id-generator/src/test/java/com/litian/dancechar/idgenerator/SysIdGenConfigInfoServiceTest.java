package com.litian.dancechar.idgenerator;

import cn.hutool.json.JSONUtil;
import com.litian.dancechar.idgenerator.core.idgenconfig.dto.SysIdGenConfigInfoReqDTO;
import com.litian.dancechar.idgenerator.core.idgenconfig.service.SysIdGenConfigInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysIdGenConfigInfoServiceTest {
    @Resource
    private SysIdGenConfigInfoService sysIdGenConfigInfoService;

    @Test
    public void testFindList() {
        System.out.print(JSONUtil.toJsonStr(sysIdGenConfigInfoService
                .findList(new SysIdGenConfigInfoReqDTO())));
    }
}
