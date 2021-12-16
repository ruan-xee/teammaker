package com.sen.springboot.dto.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@ApiModel(value = "密码登录信息", description = "用户通过账户密码登录方式")
@Data
public class PasswordLoginDto {
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotBlank(message = "请输入密码")
    private String password;
}
