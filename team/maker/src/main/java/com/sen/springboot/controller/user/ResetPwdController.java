package com.sen.springboot.controller.user;

import com.sen.springboot.common.NormalInfoEnum;
import com.sen.springboot.common.RegexpUtils;
import com.sen.springboot.common.result.Result;
import com.sen.springboot.common.result.ResultFactory;
import com.sen.springboot.dto.ResetPwd.ResetPwdDto;
import com.sen.springboot.dto.code.CodeCheckDto;
import com.sen.springboot.dto.code.PhoneCodeDto;
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

@RestController
@Slf4j
@Api(tags = "重置密码")
public class ResetPwdController {
    @Autowired
    PhoneCodeService phoneCodeService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取重置验证码")
    @PostMapping(value = "/api/reset/getcode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result getResetCode(@RequestBody @Validated PhoneCodeDto phoneCodeDto){
        String phone = phoneCodeDto.getPhone();
        if (phone == null){
            throw new ServiceException(ServiceExceptionEnum.PHONE_ARE_EMPTY);
        } else {
            if (!RegexpUtils.checkPhone(phone)){
                throw new ServiceException(ServiceExceptionEnum.ACCOUNT_FORMAT_ERROR);
            }
        }
        User user = userService.getUserByPhone(phone);
        if (user == null){
            throw new ServiceException(ServiceExceptionEnum.USER_NOT_FOUND);
        }
        phoneCodeService.sendCode(phone);
        return ResultFactory.buildSuccessResult(NormalInfoEnum.CODE_SEND_SUCCESS.getMessage());
    }

    @ApiOperation(value = "忘记密码step1验证", notes = "POST接口")
    @PostMapping(value = "/api/reset/check",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result checkOldUser(@RequestBody @Validated CodeCheckDto codeCheckDto){
        if (!phoneCodeService.checkCode(codeCheckDto.getPhone(), codeCheckDto.getCode())){
            throw new ServiceException(ServiceExceptionEnum.CODE_ERROR);
        }
        return ResultFactory.buildSuccessResult(NormalInfoEnum.CODE_CHECK_SUCCESS.getMessage());
    }

    @ApiOperation(value = "忘记密码step2重置密码", notes = "POST接口")
    @PostMapping(value = "/api/reset/pwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result resetOldUser(@RequestBody @Validated ResetPwdDto resetPwdDto){
        int status = 4;
        status = userService.updatePwdByPhone(resetPwdDto);

        switch (status) {
            case 0:
                throw new ServiceException(ServiceExceptionEnum.PASSWORD_EMPTY);
            case 1:
                return ResultFactory.buildSuccessResult(NormalInfoEnum.RESETPWD_SUCCESS.getMessage());
            case 2:
                throw new ServiceException(ServiceExceptionEnum.USER_NOT_FOUND);
            case 3:
                throw new ServiceException(ServiceExceptionEnum.SYS_ERROR);
            case 4:
                throw new ServiceException(ServiceExceptionEnum.ACCOUNT_FORMAT_ERROR);
        }
        return ResultFactory.buildFailResult(NormalInfoEnum.RESETPWD_FAIL.getMessage());

    }
}
