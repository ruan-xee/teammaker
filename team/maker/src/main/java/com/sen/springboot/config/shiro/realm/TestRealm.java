package com.sen.springboot.config.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的realm用来实现用户的认证和授权
 * 父类AuthenticatingRealm只负责用户认证（登录）
 */
public class TestRealm extends AuthorizingRealm {

    /**
     * 用户认证的方法 这个方法不能手动调用，shiro会自动调用
     * @param authenticationToken 用户身份 这里存放着用户的账号和密码
     * @return 用户登录成功后的身份证明
     * @throws AuthenticationException 如果认证失败，Shiro会抛出各种异常
     * 常用异常
     * 1、UnknownAccountException    账号不存在
     * 2、AccountException           账号异常
     * 3、LockedAccountException     账户锁定异常（冻结异常）
     * 4、IncorrectCredentialsException 密码认证失败以后shiro自动抛出表示密码错误
     * 注意：
     *  如果这些异常不够用可以自定义异常类并继承shiro认证异常父类AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();//获取页面中传递的用户账号
        String password = String.valueOf(token.getPassword());//获取页面中的用户密码，实际工作中基本不需要获取
        System.out.println(username+"--->>"+password);
        /**
         * 认证账号，这里应该是从数据库中获取数据，
         * 如果进入if表示账号不存在，要抛出异常
         */
        if (!"admin".equals(username)&&!"zhangsan".equals(username)){
            throw new UnknownAccountException();//抛出账号错误的异常
        }
        /**
         * 认证账号，这里应该根据从数据库中获取出来的数据进行逻辑判断，判断当前账号是否可用
         * IP是否允许等等，根据不同的逻辑可以抛出不同的异常
         */
        //if (!"zhangsan".equals(username)){
        //    throw new LockedAccountException();//抛出账号锁定的异常
        //}

        /**
         * 数据密码加密主要是防止数据再浏览器到后台服务器之间的数据传递时被篡改或被截获，因此应该在前台到后台的过程中
         * 进行加密，而我们这里的加密一个是在浏览器中获取后台的明码加密和对数据库中的数据进行加密
         * 这就丢失了数据加密的意义 因此不建议在这里进行加密，应该在页面传递时进行加密
         */
        //设置让当前登录用户中的密码数据进行加密
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        this.setCredentialsMatcher(credentialsMatcher);

        //对数据库中的密码进行加密
        Object obj = new SimpleHash("MD5", "123456", "", 2);
        /**
         * 创建密码认证对象，有shiro自动认证密码
         * 参数1为数据库中的账号（或页面账号均可）
         * 参数2为数据库中读取出来的密码
         * 参数3为当前Realm的名字
         * 如果密码认证成功，则返回一个用户身份对象，如果密码认证失败shiro会抛出异常IncorrectCredentialsException
         */
        return new SimpleAuthenticationInfo(username, obj, getName());
    }

    /**
     * 用户授权的方法，当用户认证通过以后将自动执行这个方法
     * 这里应该查询数据库来获取当前用户的所有角色和权限，并设置到shiro中
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户的账号，根据账号来从数据库中获取数据
        Object obj = principalCollection.getPrimaryPrincipal();
        Set<String> roles = new HashSet<>();
        //设置角色，这个操作应该是从数据库中读取数据
        //注意：由于每次点击需要授权的请求时，shiro都会执行这个方法，因此如果这里的数据是来自于数据库中的
        //     那么一定要控制好不能每次都从数据库中获取数据，这样效率太低了
        if ("admin".equals(obj)){
            roles.add("admin");
            roles.add("user");
        }
        if ("zhangsan".equals(obj)){
            roles.add("user");
        }
        Set<String> permissions = new HashSet<>();
        if ("admin".equals(obj)){
            //添加一个权限admin:add只是一种命名风格，表示admin下的add功能
            permissions.add("admin:add");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    public static void main(String[] args) {
        //使用shiro提供的工具类对数据进行加密
        //参数1为加密算法名，我们使用MD5这是个不可逆的加密算法
        //参数2为需要加密的数据
        //参数3为加密的盐值 用于改变加密结果的，不同的盐值的数据是不一致的
        //参数4为加密次数
        //一个密码加密2次和加密一次后再对加密后内容加密一次是不同的
        Object obj = new SimpleHash("MD5", "123456", "", 1);

    }
}
