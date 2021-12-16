package com.sen.springboot.exception;

import com.sen.springboot.common.result.Result;
import com.sen.springboot.common.result.ResultFactory;
import com.sun.net.httpserver.HttpsServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller全局异常处理，Controller只要抛出异常即可，具体Response由这里返回
 */
@RestControllerAdvice(basePackages = "com.sen.springboot.controller")
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public Result serviceExceptionHandler(HttpServletRequest req, ServiceException ex) {
        log.error("[ServiceExceptionHandler]", ex);
        return ResultFactory.buildFailResult(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result missingServletRequestParameterExceptionHandler(HttpsServer req, MissingServletRequestParameterException ex) {
        log.error("[MissingServletRequestParameterExceptionHandler]", ex);
        return ResultFactory.buildFailResult(ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getMessage());
    }

    //用户捕获无权限操作
    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result unauthorizedException(HttpServletRequest req,Exception ex){
        log.error("[UnauthorizedException]",ex);
        return ResultFactory.buildFailResult(ServiceExceptionEnum.USER_NOT_PERMITED.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = UnknownAccountException.class)
    public Result unknownAccountException(HttpServletRequest req,Exception ex){
        log.error("[UnknownAccountException]",ex);
        return ResultFactory.buildFailResult(ServiceExceptionEnum.USER_NOT_FOUND.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest req, Exception ex) {
        log.error("[Exception]", ex);
        return ResultFactory.buildFailResult(ServiceExceptionEnum.SYS_ERROR.getMessage());
    }
}
