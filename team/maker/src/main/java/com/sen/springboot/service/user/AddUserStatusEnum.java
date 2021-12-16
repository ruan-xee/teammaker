package com.sen.springboot.service.user;

public enum AddUserStatusEnum {
    EMPTY_PASSWORD(0),
    SUCCESS(1),
    USER_EXIST(2),
    DB_ADD_ERROR(3);

    private int status;

    AddUserStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
