package com.litian.dancechar.base;

import cn.hutool.json.JSONUtil;
import com.litian.dancechar.base.biz.student.dto.StudentReqDTO;
import com.litian.dancechar.base.biz.student.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {
    @Resource
    private StudentService studentService;

    @Test
    public void testFindList() {
        System.out.print(JSONUtil.toJsonStr(studentService.findList(new StudentReqDTO())));
    }
}
