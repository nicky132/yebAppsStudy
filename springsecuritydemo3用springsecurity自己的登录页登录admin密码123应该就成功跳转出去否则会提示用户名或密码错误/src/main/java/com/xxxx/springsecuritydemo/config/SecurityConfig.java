package com.xxxx.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * springsecurity配置类
 *
 */
@Configuration
public class SecurityConfig  {
    /**
     * 已经有这样的实例了，接下来就要去service去实现这个实例
     * @return
     */
    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

}
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //表单提交
//        http.formLogin()
//                .loginPage("/login.html");
//    }
//
//    /**
//     * 已经有这样的实例了，接下来就要去service去实现这个实例
//     * @return
//     */
//    @Bean
//    public PasswordEncoder getPw(){
//        return new BCryptPasswordEncoder();
//    }
//
//}
