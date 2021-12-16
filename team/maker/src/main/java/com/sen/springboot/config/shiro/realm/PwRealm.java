package com.sen.springboot.config.shiro.realm;

import com.sen.springboot.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.util.ByteSource;

public class PwRealm extends BaseRealm{
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException{
        String account = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByPhone(account);
        if (user == null){
            throw new UnknownAccountException();
        }
        String password = user.getPassword();
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        System.out.println(user);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(account, password, salt, getName());
        return authenticationInfo;
    }

}
