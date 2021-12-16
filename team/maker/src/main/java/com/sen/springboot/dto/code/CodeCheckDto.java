package com.sen.springboot.dto.code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "验证码校验", description = "验证")
@Data
public class CodeCheckDto {
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank
    private String code;
}
