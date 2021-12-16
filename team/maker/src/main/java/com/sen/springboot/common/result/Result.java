package com.sen.springboot.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "响应结果", description = "响应结果实体")
public class Result implements Serializable {
    @ApiModelProperty(value = "是否成功标识", dataType = "boolean")
    private Object isSuccess;
    @ApiModelProperty(value = "错误信息", dataType = "String")
    private Object errorMsg;
    @ApiModelProperty(value = "响应状态信息", dataType = "Object")
    private Object responseStatus;
    @ApiModelProperty(value = "具体响应数据", dataType = "Object")
    private Object data;

    public Result() {}

    public Result(Object isSuccess, Object errorMsg, Object responseStatus, Object data) {
        this.isSuccess = isSuccess;
        this.errorMsg = errorMsg;
        this.responseStatus = responseStatus;
        this.data = data;
    }

    public Object getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Object isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Object responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
