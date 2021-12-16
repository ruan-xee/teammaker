package com.sen.springboot.common.result;

public class ResultFactory<T> {
    public static Result buildSuccessResult(Object msg) {
        ResponseData responseData = new ResponseData(msg);
        ResponseStatus responseStatus = new ResponseStatus("Success", System.currentTimeMillis());
        return buildResult(true, "", responseStatus, responseData);
    }

    public static Result buildFailResult(Object msg) {
        ResponseData responseData = new ResponseData(msg);
        ResponseStatus responseStatus = new ResponseStatus("Fail", System.currentTimeMillis());
        return buildResult(false, msg, responseStatus, responseData);
    }

    public static Result buildUnauthorized(Object msg) {
        ResponseData responseData = new ResponseData(msg);
        ResponseStatus responseStatus = new ResponseStatus("Fail", System.currentTimeMillis());
        return buildResult(false, msg, responseStatus, responseData);
    }

    public static Result buildResult(boolean isSuccess, Object errorMsg, Object responseStatus,
                                     Object data) {
        return new Result(isSuccess, errorMsg, responseStatus, data);
    }
}
