package com.sen.springboot.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {


    @RequestMapping("/")
    public String index(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model){
        //获取权限操作对象，利用这个对象来完成登录操作
        Subject subject = SecurityUtils.getSubject();
        //登出,进入这个请求用户一定是要完成用户登录功能，因此我们就先登出，否则shiro会有缓存不能登录
        //注意：这么做，如果用户是误操作会重新执行一次登录请求
        subject.logout();
        //用户是否认证过（是否登录过），进入if表示用户没有认证过，需要进行认证
        if (!subject.isAuthenticated()){
            //创建用户认证时的身份令牌，并设置我们从页面中传递过来的账号、密码
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {

                /**
                 * 执行登录，会自动调用我们Realm对象中的认证方法
                 * 如果登录失败会抛出各种异常
                 */
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e){
                model.addAttribute("errorMsg", "账号错误！");
                return "login";
            } catch (LockedAccountException e){
                model.addAttribute("errorMsg", "账号被锁定！");
                return "login";
            } catch (IncorrectCredentialsException e){
                model.addAttribute("errorMsg", "密码错误！");
                return "login";
            } catch (AuthenticationException e){
                model.addAttribute("errorMsg", "认证失败！");
                return "login";
            }
        }
        return "redirect:/success";
    }

    @RequestMapping("/success")
    public String loginSuccess(){
        return "success";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        //登出当前账号，清空shiro当前用户的缓存，否则无法登录
        subject.logout();
        return "redirect:/";
    }

    @RequestMapping("/noPermission")
    public String noPermission(){
        return "noPermission";
    }

    /**
     * @RequireRoles这个注解是shiro提供的，用于标签类或者当前在访问方法需要什么的角色权限
     * 属性
     *      value 取值为String 数组类型 用于指定访问时所需要的角色名
     *      logical 取值为logical.AND或logical.OR，当指定多个角色时可以使用该属性指定并和或的关系，默认AND
     *              AND表示当前用户必须同时拥有多个角色才能访问
     */
    /**
     * @RequiresPermissions用于判断当前用户是否有指定的一个或多个权限
     * 用法与@RequiresRoles相同
     *
     * 注意：
     *  Shiro中除了基于配置的权限验证以及注解的权限验证以外还支持基于方法调用的权限验证，例如
     *      Subject subject = SecurityUtils.getSubject();
     *      String[] roles = {""};
     *      subject.checkRoles(roles);//验证当前用户是否拥有指定的角色
     *      String[] permissions = {""};
     *      subject.checkPermissions(permissions);//验证当前用户是否拥有指定的权限
     */
    //@RequiresRoles(value = {"admin","user"})
    //@RequiresPermissions(value = {"admin:add"})
    @RequestMapping("/admin/test")
    public @ResponseBody String adminTest(){
        return "/admin/test请求";
    }

    //@RequiresRoles(value = {"user"})
    @RequestMapping("/user/test")
    public @ResponseBody String userTest(){
        return "/user/test请求";
    }

    /**
     * 配置自定义的异常拦截，需要拦截AuthorizationException异常或ShiroException异常
     * 注意：当Shiro出现权限认证失败以后会抛出异常，因此必须要写一个自定义的异常拦截
     *      否则无法正常的转向我们的错误页面
     * @return
     */
    @ExceptionHandler(value = {AuthorizationException.class})
    public String permissionError(Throwable throwable){
        //转向到没有权限的视图页面，可以利用参数throwable将错误信息写入浏览器
        //实际工作中应该根据参数的类型来判断具体是什么异常，然后根据不同的异常来为用户提供不同的提示信息
        return "noPermission";
    }
}
