package com.litian.dancechar.base.biz.student.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.litian.dancechar.base.biz.student.dao.entity.StudentDO;
import com.litian.dancechar.base.biz.student.dao.inf.StudentDao;
import com.litian.dancechar.base.biz.student.dto.StudentReqDTO;
import com.litian.dancechar.base.biz.student.dto.StudentRespDTO;
import com.litian.dancechar.base.common.constants.RedisKeyConstants;
import com.litian.dancechar.base.framework.redislistener.common.RedisChannelEnum;
import com.litian.dancechar.base.init.ThreadPoolInit;
import com.litian.dancechar.framework.cache.publish.util.MessagePublishUtil;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 学生服務
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StudentService extends ServiceImpl<StudentDao,StudentDO> {
    @Resource
    private StudentDao studentDao;
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private MessagePublishUtil messagePublishUtil;

    /**
     * 功能: 分页查询白名单列表
     */
    public RespResult<PageWrapperDTO<StudentRespDTO>> listPaged(StudentReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<StudentRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(studentDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：查询学生信息
     */
    public StudentDO findById(String id) {
        String key = String.format(RedisKeyConstants.Student.ID_KEY, id);
        String studentFromRedis = redisHelper.get(key);
        if(StrUtil.isNotEmpty(studentFromRedis)){
            return JSONUtil.toBean(studentFromRedis, StudentDO.class);
        }
        StudentDO studentDO = studentDao.selectById(id);
        if(ObjectUtil.isNotNull(studentDO)){
            redisHelper.set(key, JSONUtil.toJsonStr(studentDO));
        }
        return studentDO;
    }

    /**
     * 功能：批量查询学生信息
     */
    public List<StudentDO> findByIds(List<String> ids) {
        return studentDao.selectBatchIds(ids);
    }


    /**
     * 功能：查询学生列表
     */
    public RespResult<List<StudentRespDTO>> findList(StudentReqDTO req) {
        return RespResult.success(studentDao.findList(req));
    }

    /**
     * 功能：新增数据
     */
    public RespResult<Boolean> saveWithInsert(StudentReqDTO studentReqDTO) {
        StudentDO studentDO = new StudentDO();
        DCBeanUtil.copyNotNull(studentDO, studentReqDTO);
        save(studentDO);
        redisHelper.set("nane", "1233");
        System.out.println(redisHelper.get("name"));
        messagePublishUtil.sendMsg(RedisChannelEnum.EXAMPLE_CHANNEL.getCode(),JSONUtil.toJsonStr(studentDO));
        return RespResult.success(true);
    }

    /**
     * 功能：单条插入百万数据
     */
    public RespResult<Boolean> saveStuListWithOne() {
        StopWatch stopWatch = StopWatch.createStarted();
        List<StudentDO> studentList = buildStudentList(1000000);
        for(StudentDO studentDO : studentList){
            save(studentDO);
        }
        log.info("通过for完成百万数据插入！！！总耗时：{}s", stopWatch.getTime()/1000);
        return RespResult.success(true);
    }

    /**
     * 功能：批量插入数据
     */
    public RespResult<Boolean> saveStuListWithBatch() {
        StopWatch stopWatch = StopWatch.createStarted();
        List<StudentDO> studentList = buildStudentList(1000000);
        this.saveBatch(studentList);
        log.info("批量插入百万数据！！！总耗时：{}s", stopWatch.getTime()/1000);
        return RespResult.success(true);
    }


    /**
     * 功能：通过线程池批量插入数据
     */
    public RespResult<Boolean> saveStuListWithThreadPoolBatch(){
        StopWatch stopWatch = StopWatch.createStarted();
        // 每次插入1000条数据
        List<StudentDO> studentList = this.buildStudentList(1000000);
        int dataSize = studentList.size();
        int step = 1000;
        int totalTasks = (dataSize % step == 0 ? dataSize/step : (dataSize/step + 1));
        final CountDownLatch countDownLatch = new CountDownLatch(totalTasks);
        for(int j = 0; j < dataSize; j=j+step){
            final int start = j;
            final int perCount = (dataSize - start) < step ? (dataSize - start) : step;
            ThreadPoolInit.getStudentThreadPoolTaskExecutor().execute(()->{
                try {
                    log.info("多线程开始: start == " + start + " , 多线程个数count" + perCount);
                    saveBatch(studentList.subList(start,perCount+start));
                    countDownLatch.countDown();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("通过线程池批量插入百万数据！！！总耗时：{}s", stopWatch.getTime()/1000);
        return RespResult.success(true);
    }

    private List<StudentDO> buildStudentList(int dataSize){
        List<StudentDO> studentList = Lists.newArrayList();
        String noPrefix = RandomUtil.randomNumbers(3);
        for (int i=0; i<dataSize;i++){
            StudentDO studentDO = new StudentDO();
            studentDO.setNo(noPrefix+ i);
            studentList.add(studentDO);
        }
        return studentList;
    }
}