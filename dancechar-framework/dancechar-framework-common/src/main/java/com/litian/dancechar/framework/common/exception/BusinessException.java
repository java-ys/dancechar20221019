package com.litian.dancechar.framework.common.exception;

/**
 * 业务异常类
 *
 * @author tojson
 * @date 2021/6/14 21:15
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message,cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
        super(message,cause, enableSuppression, writeableStackTrace);
    }

}
