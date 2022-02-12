package com.xxxx.springsecuritydemo.controlller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {
    /**
     * 登录
     * @return
     */
    @RequestMapping("login")
    public String login(){
        System.out.println("执行登录方法");
        return "redirect:main.html";
    }
}
