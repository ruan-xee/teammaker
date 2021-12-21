package com.sen.springboot.controller.user;

import com.sen.springboot.common.NormalInfoEnum;
import com.sen.springboot.common.RegexpUtils;
import com.sen.springboot.common.result.Result;
import com.sen.springboot.common.result.ResultFactory;
import com.sen.springboot.dto.code.CodeCheckDto;
import com.sen.springboot.dto.code.PhoneCodeDto;
import com.sen.springboot.dto.register.RegisterDto;
import com.sen.springboot.exception.ServiceException;
import com.sen.springboot.exception.ServiceExceptionEnum;
import com.sen.springboot.model.User;
import com.sen.springboot.service.user.PhoneCodeService;
import com.sen.springboot.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@Api(tags = "注册管理")
public class RegisterController {
    @Autowired
    PhoneCodeService phoneCodeService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取注册验证码")
    @PostMapping(value = "/api/register/getcode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result getRegisterCode(@RequestBody @Validated PhoneCodeDto phoneCodeDto){
        String phone = phoneCodeDto.getPhone();
        if (Objects.isNull(phone)){
            throw new ServiceException(ServiceExceptionEnum.PHONE_ARE_EMPTY);
        } else {
            if (!RegexpUtils.checkPhone(phone)){
                throw new ServiceException(ServiceExceptionEnum.ACCOUNT_FORMAT_ERROR);
            }
        }
        User user = userService.getUserByPhone(phone);
        if (!Objects.isNull(user)){
            throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXIST);
        }
        phoneCodeService.sendCode(phone);
        return ResultFactory.buildSuccessResult(NormalInfoEnum.CODE_SEND_SUCCESS.getMessage());
    }

    @ApiOperation(value = "注册step1验证", notes = "注册POST接口")
    @PostMapping(value = "/api/register/check", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result checkNewUser(@RequestBody @Validated CodeCheckDto codeCheckDto) {
        if (!phoneCodeService.checkCode(codeCheckDto.getPhone(), codeCheckDto.getCode())){
            throw new ServiceException(ServiceExceptionEnum.CODE_ERROR);
        }
        return ResultFactory.buildSuccessResult(NormalInfoEnum.CODE_CHECK_SUCCESS.getMessage());
    }

    @ApiOperation(value = "注册step2设置密码", notes = "注册POST接口")
    @PostMapping(value = "/api/register/pwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result addNewUser(@RequestBody @Validated RegisterDto registerDto){
        if (!phoneCodeService.isExistCode(registerDto.getPhone())){
            throw new ServiceException(ServiceExceptionEnum.USER_AUTHENTICATED_TIMEOUT);
        }
        int status = 4;
        status = userService.addUserByPhone(registerDto);
        switch (status) {
            case 0:
                throw new ServiceException(ServiceExceptionEnum.PASSWORD_EMPTY);
            case 1:
                return ResultFactory.buildSuccessResult(NormalInfoEnum.REGISTER_SUCCESS.getMessage());
            case 2:
                throw new ServiceException(ServiceExceptionEnum.USER_ALREADY_EXIST);
            case 3:
                throw new ServiceException(ServiceExceptionEnum.SYS_ERROR);
            case 4:
                throw new ServiceException(ServiceExceptionEnum.ACCOUNT_FORMAT_ERROR);
        }
        return ResultFactory.buildFailResult(NormalInfoEnum.REGISTER_FAIL.getMessage());
    }
}
