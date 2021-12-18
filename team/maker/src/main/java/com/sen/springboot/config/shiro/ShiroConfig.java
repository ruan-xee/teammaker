package com.sen.springboot.config.shiro;

import com.sen.springboot.config.shiro.realm.PwRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private SessionManager sessionManager;

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 配置一个安全管理器
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(pwRealm());
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        defaultWebSecurityManager.setCacheManager(cacheManager);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        return defaultWebSecurityManager;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey("EVANNIGHTLY_WAOU".getBytes());
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(3600*24*7);  //设置rememberme的cookie过期时间是一周
        return simpleCookie;
    }

    //配置一个自定义的Realm的bean，最终将使用这个bean返回的对象来完成我们的认证和授权
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置用户登录请求 如果需要进行登录时Shiro就会转到这个请求进入登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        /**
         * 配置权限拦截规则
         */
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/api/**", "anon");//配置登录请求不需要认证 anon表示某个请求不需要认证
        filterChainMap.put("/view/**", "authc");
        filterChainMap.put("/api/logout", "logout");//配置登出请求，登出后会清空当前用户的内存
        //配置一个admin开头的所有请求需要登录，authc表示需要登录认证
        //roles[admin]表示所有以admin开头的请求需要有admin的角色才可以使用
        //filterChainMap.put("/**", "authc");//配置剩余的所有请求全部需要进行登录认证，这个必须写在最后，可选配置

        //设置权限拦截规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器，Realm中SimpleAuthenticationInfo需要做的
     **/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public PwRealm pwRealm() {
        PwRealm pwRealm = new PwRealm();
        //配置自定义密码比较器
        pwRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return pwRealm;
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
