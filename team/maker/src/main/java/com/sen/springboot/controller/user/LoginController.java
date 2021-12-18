package com.sen.springboot.controller.user;

import com.sen.springboot.common.NormalInfoEnum;
import com.sen.springboot.common.RegexpUtils;
import com.sen.springboot.common.result.Result;
import com.sen.springboot.common.result.ResultFactory;
import com.sen.springboot.dto.code.CodeCheckDto;
import com.sen.springboot.dto.code.PhoneCodeDto;
import com.sen.springboot.dto.login.PasswordLoginDto;
import com.sen.springboot.exception.ServiceException;
import com.sen.springboot.exception.ServiceExceptionEnum;
import com.sen.springboot.model.User;
import com.sen.springboot.service.user.PhoneCodeService;
import com.sen.springboot.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@Slf4j
@Api(tags = "登录验证")
public class LoginController {

    @Resource
    UserService userService;
    @Resource
    PhoneCodeService phoneCodeService;

    private UsernamePasswordToken tokenLogin(UsernamePasswordToken token) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            if (!subject.isAuthenticated()) {
                throw new ServiceException(ServiceExceptionEnum.USER_NOT_AUTHENTICATED);
            }
            return token;
        } catch (AuthenticationException exception) {
            throw new ServiceException(ServiceExceptionEnum.USER_ERROR);
        }
    }

    @ApiOperation(value = "获取登录验证码")
    @PostMapping(value = "/api/login/getcode", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result getLoginCode(@RequestBody @Validated PhoneCodeDto phoneCodeDto){
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

    @ApiOperation(value = "验证码登录", notes = "登录POST接口")
    @PostMapping(value = "/api/login/bycode", consumes = MediaType.APPLICATION_JSON_VALUE) //consumes匹配Content-Type
    public Result login(@RequestBody @Validated CodeCheckDto codeCheckDto) {
        String phone = codeCheckDto.getPhone();

        if (!RegexpUtils.checkPhone(phone)) {
            throw new ServiceException(ServiceExceptionEnum.ACCOUNT_FORMAT_ERROR);
        }

        if (!phoneCodeService.checkCode(phone, codeCheckDto.getCode())) {
            throw new ServiceException(ServiceExceptionEnum.CODE_ERROR);
        }
        User user = userService.getUserByPhone(phone);
        UsernamePasswordToken phoneToken = new UsernamePasswordToken(phone,user.getPassword());
        return ResultFactory.buildSuccessResult(phoneToken.toString());
    }

    @ApiOperation(value = "密码登录", notes = "密码登录POST接口")
    @PostMapping(value = "/api/login/pwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    //consumes匹配Content-Type
    public Result loginByPassword(@RequestBody @Validated PasswordLoginDto requestUser) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(requestUser.getPhone(), requestUser.getPassword());
        UsernamePasswordToken token = tokenLogin(usernamePasswordToken);
        return ResultFactory.buildSuccessResult(token.toString());
    }

    @ApiOperation(value = "用户登出", notes = "用户登出GET接口")
    @GetMapping("/api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("成功登出");
    }

    @ApiOperation(value = "用户认证", notes = "用户认证是否有权限访问")
    @GetMapping("/authentication")
    public Result authentication() {
        return ResultFactory.buildSuccessResult("认证成功");
    }
}
