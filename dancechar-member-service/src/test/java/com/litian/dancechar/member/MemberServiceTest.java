package com.litian.dancechar.member;

import cn.hutool.json.JSONUtil;
import com.litian.dancechar.member.biz.integral.dto.IntegralInfoReqDTO;
import com.litian.dancechar.member.biz.integral.service.IntegralInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {
    @Resource
    private IntegralInfoService integralInfoService;

    @Test
    public void testFindList() {
        System.out.print(JSONUtil.toJsonStr(integralInfoService.findList(new IntegralInfoReqDTO())));
    }
}
