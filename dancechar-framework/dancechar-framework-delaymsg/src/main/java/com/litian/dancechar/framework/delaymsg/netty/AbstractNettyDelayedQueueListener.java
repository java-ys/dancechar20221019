package com.litian.dancechar.framework.delaymsg.netty;


import cn.hutool.json.JSONUtil;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

/**
 * 时间轮算法-抽象定时任务
 *
 * @author tojson
 * @date 2022/09/28 20:53
 */
@Slf4j
public abstract class AbstractNettyDelayedQueueListener<T> implements TimerTask {

    @Override
    public synchronized void run(Timeout timeout) throws Exception {
        try{
            long start = System.currentTimeMillis();
            log.info("开始执行业务逻辑，data：{}", JSONUtil.toJsonStr(taskVO));
            execute(taskVO);
            log.info("执行业务逻辑结束，总耗时：{}ms", System.currentTimeMillis() -start);
        } catch (Exception e){
            log.error("执行延时任务逻辑系统异常！errMsg:{}", e.getMessage(), e);
        }
    }

    /**
     * 具体的业务执行逻辑
     */
    protected abstract  void execute(TaskVO<T> taskVO);

    /**
     * 任务VO对象
     */
    private TaskVO<T> taskVO;

    public TaskVO<T> getTaskVO() {
        return taskVO;
    }

    public void setTaskVO(TaskVO<T> taskVO) {
        this.taskVO = taskVO;
    }

    public void buildData(T data){
        TaskVO<T> taskVO = new TaskVO<>();
        taskVO.setData(data);
        this.setTaskVO(taskVO);
    }
}
