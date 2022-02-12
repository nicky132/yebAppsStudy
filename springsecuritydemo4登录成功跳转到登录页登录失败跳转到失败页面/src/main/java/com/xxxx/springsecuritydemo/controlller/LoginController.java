package com.xxxx.springsecuritydemo.controlller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


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
}
