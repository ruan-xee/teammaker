package com.sen.springboot.config.shiro.realm;

import com.sen.springboot.common.RegexpUtils;
import com.sen.springboot.model.Permission;
import com.sen.springboot.model.Role;
import com.sen.springboot.model.User;
import com.sen.springboot.service.ShiroService;
import com.sen.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class BaseRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    ShiroService shiroService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String account =principalCollection.getPrimaryPrincipal().toString();
        boolean isPhone = RegexpUtils.checkPhone(account);
        if (!isPhone){
            throw new UnknownAccountException();
        }
        User user = null;
        user = userService.getUserByPhone(account);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (user == null){
            throw new UnknownAccountException();
        }

        //获取用户的角色
        Set<Role> roleSet = shiroService.getUserRoles(user.getId());
        Set<String> roleString = roleSet.stream().map(Role::getRole).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleString);
        log.info(roleSet.toString());

        //获取用户权限
        Set<Permission> permitSet = shiroService.getUserPermissions(user.getId());
        Set<String> permitString = permitSet.stream().map(Permission::getPermission).collect(Collectors.toSet());
        simpleAuthorizationInfo.setStringPermissions(permitString);
        log.info(permitSet.toString());

        return simpleAuthorizationInfo;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
