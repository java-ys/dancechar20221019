package com.litian.dancechar.framework.common.exception;

/**
 *
 * sentinel限流后异常类
 *
 * @author tojson
 * @date 2022/01/18 17:53
 */
public class SentinelFlowLimitException extends RuntimeException{

    public SentinelFlowLimitException(String msg){
        super(msg);
    }

    public SentinelFlowLimitException(Throwable cause){
        super(cause);
    }

    private Object hotKey;

    public SentinelFlowLimitException(Object hotKey) {
        super("热点参数 [" + hotKey + "]限流！");
        this.hotKey = hotKey;
    }

    public Object getHotKey() {
        return hotKey;
    }
}
