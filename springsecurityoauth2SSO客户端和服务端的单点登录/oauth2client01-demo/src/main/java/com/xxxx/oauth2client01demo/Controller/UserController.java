package com.xxxx.oauth2client01demo.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取当前登录的用户信息
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 获取当前用户信息
     * @param authentication
     * @return
     */
    @RequestMapping("getCurrentUser")

    public  Object getCurrentUser(Authentication authentication){
        return authentication;
    }
}
