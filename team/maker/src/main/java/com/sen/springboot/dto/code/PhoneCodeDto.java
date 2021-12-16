package com.sen.springboot.dto.code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "短信获取验证码", description = "短信注册获取验证码")
@Data
public class PhoneCodeDto {
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
}
