package com.xxxx.oauth2client01demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
//开启单点登录功能
@EnableOAuth2Sso
public class Oauth2client01DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2client01DemoApplication.class, args);
		//登录客户端用http://localhost:8081/user/getCurrentUser获取用户信息时会自动跳到http://localhost:8080/login的授权服务器登录
		//登录成功后自动跳转到客户端http://8081/user/getCurrentUser再拿到到用户的个人信息
    }

}
