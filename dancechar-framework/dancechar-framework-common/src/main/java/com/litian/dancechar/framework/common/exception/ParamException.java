package com.litian.dancechar.framework.common.exception;

import lombok.NoArgsConstructor;

/**
 * 参数校验异常
 *
 * @author tojson
 * @date 2022/6/23 21:15
 */
@NoArgsConstructor
public class ParamException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ParamException(String message) {
        super(message);
    }

    public ParamException(int code, String message) {
        super(message);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
