package com.litian.dancechar.framework.common.exception;

import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.base.RespResultCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常类
 *
 * @author tojson
 * @date 2021/6/14 21:15
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RespResult<Void> handleError(MethodArgumentTypeMismatchException e) {
        log.error("Method Argument Type Mismatch:{}", e.getMessage(), e);
        return RespResult.error("Method Argument Type Mismatch");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RespResult<Void> handleError(MissingServletRequestParameterException e) {
        log.error("Method Request Parameter :{}", e.getMessage(), e);
        return RespResult.error("Method Request Parameter");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespResult<Void> handleError(MethodArgumentNotValidException e) {
        log.error("Method Request Not Valid :{}", e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String message = String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage());
        return RespResult.error(RespResultCode.ERR_PARAM_NOT_LEGAL.getCode(),
                RespResultCode.ERR_PARAM_NOT_LEGAL.getMessage(),
                message);
    }

    @ExceptionHandler(BindException.class)
    public RespResult<Void> handleError(BindException e) {
        log.error("Bind Exception :{}", e.getMessage(), e);
        FieldError fieldError = e.getFieldError();
        String message = String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage());
        return RespResult.error(RespResultCode.ERR_PARAM_NOT_LEGAL.getCode(), message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RespResult<Void> handleError(ConstraintViolationException e) {
        log.error("Constraint Exception :{}", e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("%s:%s", path, violation.getMessage());
        return RespResult.error(RespResultCode.ERR_CONSTRAINT_VIOLATION.getCode(), message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RespResult<Void> handleError(HttpMessageNotReadableException e) {
        log.error("Http Message Not Readable Exception :{}", e.getMessage(), e);
        return RespResult.error("请求的参数类型与方法接收的参数类型不匹配, 请检查！");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespResult<Void> handleError(HttpRequestMethodNotSupportedException e) {
        log.error("Http Request Method Not Support Exception :{}", e.getMessage(), e);
        String message = String.format("%s方法类型不支持,请检查！", e.getMethod());
        return RespResult.error(message);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RespResult<Void> handleError(HttpMediaTypeNotSupportedException e) {
        log.error("Http Media Type Not Support Exception :{}", e.getMessage(), e);
        String message = String.format("不支持%s content-type，请检查！", e.getContentType());
        return RespResult.error(message);
    }

    @ExceptionHandler(ParamException.class)
    public RespResult<Void> handleError(ParamException e) {
        log.error("参数不合法 :{}", e.getMessage(), e);
        return RespResult.error(RespResultCode.ERR_PARAM_NOT_LEGAL.getCode(),
                RespResultCode.ERR_PARAM_NOT_LEGAL.getMessage(), e.getMessage());
    }

    @ExceptionHandler(DistributeLockException.class)
    public RespResult<Void> handleError(DistributeLockException e) {
        log.error("分布式锁Distribute Lock Exception :{}", e.getMessage(), e);
        return RespResult.error(RespResultCode.REPEATED_OPERATE.getCode(),
                RespResultCode.REPEATED_OPERATE.getMessage(), e.getMessage());
    }

    /**
     * 热点参数限流异常
     * @param e 异常对象
     * @return ResponseResult 全局异常响应
     */
    @ExceptionHandler(SentinelFlowLimitException.class)
    public RespResult<String> sentinelFlowLimitException(SentinelFlowLimitException e) {
        log.error("sentinel热点参数key：{}被限流!errMsg:{}", e.getHotKey(), e.getMessage(), e);
        return RespResult.error(RespResultCode.REPEATED_BUSY.getCode(),
                RespResultCode.REPEATED_BUSY.getMessage(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public RespResult<Void> handleError(BusinessException e) {
        log.error("业务出现异常 :{}", e.getMessage(), e);
        return RespResult.error(RespResultCode.SYS_EXCEPTION.getCode(),
                RespResultCode.SYS_EXCEPTION.getMessage(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    //@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "系统内部错误")
    public RespResult<Void> handleError(Exception e) {
        log.error("全局异常信息，异常堆栈信息 :{}", e.getMessage(), e);
        return RespResult.error(RespResultCode.SYS_EXCEPTION.getCode(),
                RespResultCode.SYS_EXCEPTION.getMessage(), e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public RespResult<Void> handleError(Throwable e) {
        log.error("Throwable Server Exception :{}", e.getMessage(), e);
        return RespResult.error(RespResultCode.SYS_EXCEPTION.getCode(),
                RespResultCode.SYS_EXCEPTION.getMessage(), e.getMessage());
    }
}
