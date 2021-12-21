package com.sen.springboot.exception;

public enum ServiceExceptionEnum {
    // ========== 系统级别 ==========
    SUCCESS("成功"),
    SYS_ERROR("服务器未知错误"),
    MISSING_REQUEST_PARAM_ERROR("请求缺少参数"),

    // ========== 用户模块 ==========
    USER_NOT_FOUND("用户不存在"),
    USER_ERROR("用户名或密码错误"),
    PASSWORD_EMPTY("密码不能为空"),
    USER_ALREADY_EXIST("该用户已经存在"),
    USER_ALREADY_LOGIN("请勿重复登录"),
    USER_NOT_AUTHENTICATED("用户验证失败"),
    USER_NOT_LOGIN("用户未登录"),
    USER_NOT_PERMITED("用户无权限"),
    PHONE_ARE_EMPTY("请绑定手机号"),
    CODE_ERROR("验证码错误"),
    CODE_INVALID("验证码已失效"),
    USER_AUTHENTICATED_TIMEOUT("用户认证超时"),
    CODE_SEND_FREQUENTY("请勿频繁发送"),
    ACCOUNT_FORMAT_ERROR("手机号格式错误"),
    REGISTER_DUPLICATED("该用户已经注册");
    /**
     * 错误提示
     */
    private final String message;

    ServiceExceptionEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
