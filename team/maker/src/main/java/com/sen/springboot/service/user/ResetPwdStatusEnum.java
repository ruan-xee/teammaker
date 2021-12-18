package com.sen.springboot.service.user;

public enum ResetPwdStatusEnum {
    EMPTY_PASSWORD(0),
    SUCCESS(1),
    USER_NOT_EXIST(2),
    DB_RESET_ERROR(3);

    private int status;

    ResetPwdStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
