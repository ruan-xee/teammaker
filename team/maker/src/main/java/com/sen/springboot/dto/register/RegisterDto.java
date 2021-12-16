package com.sen.springboot.dto.register;

import com.sen.springboot.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "注册", description = "注册接口")
@Data
public class RegisterDto {
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String password;


    public User transToUser(){
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setUsername(phone);
        return user;
    }
}
