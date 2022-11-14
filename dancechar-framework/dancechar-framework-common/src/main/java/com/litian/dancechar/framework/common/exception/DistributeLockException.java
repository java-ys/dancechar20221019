package com.litian.dancechar.framework.common.exception;

/**
 *
 * 分布式锁异常类
 *
 * @author tojson
 * @date 2022/01/18 17:53
 */
public class DistributeLockException extends RuntimeException{

    public DistributeLockException(String msg){
        super(msg);
    }

    public DistributeLockException(Throwable cause){
        super(cause);
    }
}
