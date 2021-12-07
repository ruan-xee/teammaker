package com.sen.springboot.config.shiro;

import com.sen.springboot.config.shiro.realm.PwRealm;
import com.sen.springboot.config.shiro.realm.TestRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 配置一个安全管理器
     */
    @Bean
    public SecurityManager securityManager(Realm testRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(testRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public PwRealm pwRealm(){
        return new PwRealm();
    }

    @Bean
    public TestRealm testRealm(){return new TestRealm();}

    //配置一个自定义的Realm的bean，最终将使用这个bean返回的对象来完成我们的认证和授权
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置用户登录请求 如果需要进行登录时Shiro就会转到这个请求进入登录页面
        shiroFilterFactoryBean.setLoginUrl("/");
        //配置登录成功后转向的请求地址
        shiroFilterFactoryBean.setSuccessUrl("/success");
        //配置没有权限时转向的请求地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/noPermission");
        /**
         * 配置权限拦截规则
         */
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/login", "anon");//配置登录请求不需要认证 anon表示某个请求不需要认证
        filterChainMap.put("/logout", "logout");//配置登出请求，登出后会清空当前用户的内存
        //配置一个admin开头的所有请求需要登录，authc表示需要登录认证
        //roles[admin]表示所有以admin开头的请求需要有admin的角色才可以使用
        filterChainMap.put("/admin/**", "authc,roles[admin]");
        filterChainMap.put("/user/**", "authc");//配置一个user开头的所有请求需要登录，authc表示需要登录认证
        //filterChainMap.put("/**", "authc");//配置剩余的所有请求全部需要进行登录认证，这个必须写在最后，可选配置

        //设置权限拦截规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro的注解支持
     * shiro的注解需要借助springAOP来实现
     * @RequiresRoles()
     * @RequiresPermissions()
     *
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    //开启aop的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
