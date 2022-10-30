package com.droplet.handler.exception;

import com.droplet.domain.ResponseResult;
import com.droplet.enums.AppHttpCodeEnum;
import com.droplet.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException systemException) {
        log.error("出现系统异常！{}", systemException);
        return ResponseResult.errorResult(systemException.getCode(), systemException.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception exception) {
        log.error("出现异常！{}", exception);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), exception.getMessage());
    }
}
