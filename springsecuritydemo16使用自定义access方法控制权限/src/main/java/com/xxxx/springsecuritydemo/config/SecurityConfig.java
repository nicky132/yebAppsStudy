package com.xxxx.springsecuritydemo.config;

import com.xxxx.springsecuritydemo.handle.MyAccessDeniedHandler;
import com.xxxx.springsecuritydemo.handle.MyAuthenticationFailureHandler;
import com.xxxx.springsecuritydemo.handle.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * springsecurity配置类
 *
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //应用MyAccessDeniedHandler类
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单提交
        http.formLogin()
                //给表单设置参数别名，但是一定要更表单的name对应上
                .usernameParameter("username123")
                .passwordParameter("password123")
                //当发现/login时认为是登录，必须和表单的提交地址一样，去执行UserDetailsServiceImpl逻辑
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                //There was an unexpected error (type=Method Not Allowed, status=405).登录成功后跳转页面必须是post请求
//                .successForwardUrl("/main.html");
                //去controller里加跳转页面的方法
                //登录成功后跳转页面，post请求
//                .successForwardUrl("/toMain")
                //登录失败后跳转页面，post请求
//                .failureForwardUrl("/toError");
                //登录成功后处理器，不能和successForwardUrl共存不，然会报错
                .successHandler(new MyAuthenticationSuccessHandler("/main.html"))
                .failureForwardUrl("/toError");
        //登录失败后处理器，failureForwardUrl，然会报错
//                .failureHandler(new MyAuthenticationFailureHandler("error.html"));
        //授权认证
        http.authorizeRequests()
                //error.html不需要被认证
                .antMatchers("/error.html").permitAll()
                //login.html不需要被认证
//                .antMatchers("/login.html").permitAll()
                .antMatchers("/login.html").access("permitAll()")
                .antMatchers("/js/**","/css/**","/images/**").permitAll()
//                .antMatchers("/**/*.png").permitAll()
//                .regexMatchers(".+[.]png").permitAll()
//                .regexMatchers("/demo").permitAll()
//                .regexMatchers(HttpMethod.POST,"/demo").permitAll()
//                .antMatchers("/xxxx/demo").permitAll()
//                .mvcMatchers("/demo").servletPath("/xxxx").permitAll()
//                .antMatchers("/main1.html").hasAuthority("admin")
//                .antMatchers("/main1.html").hasAnyAuthority("admin","admiN")
//                .antMatchers("/main1.html").hasRole("abC")
//                .antMatchers("/main1.html").hasAnyRole("abC","abc")
//                .antMatchers("/main1.html").hasIpAddress("127.0.0.1")
                .antMatchers("/main1.html").access("hasRole('abc')")
                //所有请求都必须被认证，必须登录之后被访问
//                .anyRequest().authenticated();
                  .anyRequest().access("@myServicelmpl.hasPermission(request,authentication)");
        //关闭防火墙
        http.csrf().disable();

        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);
    }

    /**
     * 已经有这样的实例了，接下来就要去service去实现这个实例
     * @return
     */
    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

}
