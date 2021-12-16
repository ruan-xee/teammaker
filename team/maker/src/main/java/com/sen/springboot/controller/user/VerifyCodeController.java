package com.sen.springboot.controller.user;

import com.sen.springboot.common.NormalInfoEnum;
import com.sen.springboot.common.RegexpUtils;
import com.sen.springboot.common.result.Result;
import com.sen.springboot.common.result.ResultFactory;
import com.sen.springboot.dto.code.PhoneCodeDto;
import com.sen.springboot.exception.ServiceException;
import com.sen.springboot.exception.ServiceExceptionEnum;
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
@Api(tags = "验证码管理")
public class VerifyCodeController {
    @Autowired
    PhoneCodeService phoneCodeService;

    @ApiOperation(value = "获取注册验证码")
    @PostMapping(value = "/api/code", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result getCode(@RequestBody @Validated PhoneCodeDto phoneCodeDto){
        String phone = phoneCodeDto.getPhone();
        if (phone == null){
            throw new ServiceException(ServiceExceptionEnum.PHONE_ARE_EMPTY);
        } else {
            if (!RegexpUtils.checkPhone(phone)){
                throw new ServiceException(ServiceExceptionEnum.ACCOUNT_FORMAT_ERROR);
            }
        }
        phoneCodeService.sendRegisterCode(phone);
        return ResultFactory.buildSuccessResult(NormalInfoEnum.CODE_SEND_SUCCESS.getMessage());
    }
}
