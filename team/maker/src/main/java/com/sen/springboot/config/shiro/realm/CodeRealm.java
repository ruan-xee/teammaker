package com.sen.springboot.config.shiro.realm;

import com.sen.springboot.config.shiro.token.PhoneToken;
import com.sen.springboot.model.User;
import com.sen.springboot.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
public class CodeRealm extends BaseRealm {
    @Resource
    UserService userService;


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        PhoneToken token = null;
        if (authenticationToken instanceof PhoneToken) {
            token = (PhoneToken) authenticationToken;
        } else {
            return null;
        }
        String phone = (String) token.getPrincipal();
        User user = userService.getUserByPhone(phone);
        if (Objects.isNull(user)){
            throw new UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(user, phone, this.getName());
    }
}
