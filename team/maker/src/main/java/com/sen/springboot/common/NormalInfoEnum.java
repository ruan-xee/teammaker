package com.sen.springboot.common;

public enum NormalInfoEnum {
    // ========== 用户模块 ==========
    REGISTER_SUCCESS("注册成功"),
    REGISTER_FAIL("注册失败"),
    CODE_SEND_SUCCESS("验证码已发送，5分钟内有效"),
    CODE_CHECK_SUCCESS("验证成功");

    private final String message;

    NormalInfoEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
