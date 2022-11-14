package com.litian.dancechar.base.biz.student.job;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.litian.dancechar.base.biz.student.dao.entity.StudentDO;
import com.litian.dancechar.base.biz.student.service.StudentService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class StudentXxlJobHandler {
    @Resource
    private StudentService studentService;

    @XxlJob(value = "studentXxJobHandler")
    public void execute() throws Exception {
        String randomId = RandomUtil.randomString(100);
        log.info("XXL-JOB, Hello World start! id:{}", randomId);
        StudentDO studentDO = studentService.findById(randomId);
        log.info("findById-{}", JSONUtil.toJsonStr(studentDO));
        log.info("XXL-JOB, Hello World end id:{}", randomId);
    }
}
