package com.litian.dancechar.base.biz.student.controller;

import com.litian.dancechar.base.biz.student.dto.StudentReqDTO;
import com.litian.dancechar.base.biz.student.dto.StudentRespDTO;
import com.litian.dancechar.base.biz.student.service.StudentService;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
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
 * 学生业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "学生相关api")
@RestController
@Slf4j
@RequestMapping(value = "/sys/student/")
public class StudentController {
    @Resource
    private StudentService studentService;

    @ApiOperation(value = "分页查询列表", notes = "分页查询列表")
    @PostMapping("listPaged")
    public RespResult<PageWrapperDTO<StudentRespDTO>> listPaged(@RequestBody StudentReqDTO req) {
        return studentService.listPaged(req);
    }

    @ApiOperation(value = "根据Id查询信息", notes = "根据Id查询信息")
    @PostMapping("findById")
    public RespResult<StudentRespDTO> findById(@RequestBody StudentReqDTO req) {
        return RespResult.success(DCBeanUtil.copyNotNull(new StudentRespDTO(), studentService.findById(req.getId())));
    }
    @ApiOperation(value = "批量根据Id查询信息", notes = "批量根据Id查询信息")
    @PostMapping("findByIds")
    public RespResult<List<StudentRespDTO>> findByIds(@RequestBody StudentReqDTO req) {
        return RespResult.success(DCBeanUtil.copyList(studentService.findByIds(req.getIds()),StudentRespDTO.class));
    }

    @ApiOperation(value = "新增保存", notes = "新增保存")
    @PostMapping("saveWithInsert")
    public RespResult<Boolean> saveWithInsert(@RequestBody StudentReqDTO req) {
        log.info("新增保存数据....");
        return studentService.saveWithInsert(req);
    }

    @ApiOperation(value = "单条插入百万数据", notes = "单条插入百万数据")
    @PostMapping("saveStuListWithOne")
    public RespResult<Boolean> saveStuListWithOne() {
        log.info("单条插入百万数据....");
        return studentService.saveStuListWithOne();
    }

    @ApiOperation(value = "批量插入百万数据", notes = "批量插入百万数据")
    @PostMapping("saveStuListWithBatch")
    public RespResult<Boolean> saveStuListWithBatch() {
        log.info("批量插入百万数据....");
        return studentService.saveStuListWithBatch();
    }

    @ApiOperation(value = "通过线程池批量插入数据", notes = "通过线程池批量插入数据")
    @PostMapping("saveStuListWithThreadPoolBatch")
    public RespResult<Boolean> saveStuListWithThreadPoolBatch() {
        log.info("通过线程池批量插入百万数据....");
        return studentService.saveStuListWithThreadPoolBatch();
    }
}