package com.sen.springboot.common.result;

public class ResponseData {
    private Object message;

    public ResponseData() {
    }

    public ResponseData(Object message) {
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
