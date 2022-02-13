package com.xxxx.springsecuritydemo.controlller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {
//    /**
//     * 登录
//     * @return
//     */
//    @RequestMapping("login")
//    public String login(){
//        System.out.println("执行登录方法");
//        return "redirect:main.html";
//    }
//      直接走配置类里的login里，不需要
    /**
     * 跳转页面
     * @return
     */
//    @Secured("ROLE_abc")
//    PreAuthorize允许ROLE_开头，也可以不ROLE_开头，配置类不允许ROLE_开头
    @PreAuthorize("hasRole('ROLE_abc')")
    @RequestMapping("toMain")
    public String toMain(){
        return "redirect:main.html";
    }
    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("toError")
    public String toError(){
        return "redirect:error.html";
    }

    /**
     * 跳转页面
     * @return
     */
    @GetMapping("demo")
    @ResponseBody
    public String demo(){
        return "demo";
    }
}
