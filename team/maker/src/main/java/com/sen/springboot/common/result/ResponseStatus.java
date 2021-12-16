package com.sen.springboot.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "响应状态", description = "响应状态实体",parent = Result.class)
public class ResponseStatus {
    @ApiModelProperty(value = "应答标识", dataType = "String")
    private String ack;
    @ApiModelProperty(value = "响应时间戳", dataType = "long")
    private long timeStamp;

    public ResponseStatus() {
    }

    public ResponseStatus(String ack, long timeStamp) {
        this.ack = ack;
        this.timeStamp = timeStamp;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
