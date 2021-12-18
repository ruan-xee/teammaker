package com.sen.springboot.dto.ResetPwd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "重置密码")
public class ResetPwdDto {
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String password;
}
