package com.xxxx.springsecurityoauth2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springsecurityoauth2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springsecurityoauth2DemoApplication.class, args);
    }
    //用postman发送post请求 http://localhost:8080/oauth/token ，
    // Authorization类型为basic auth模式，Username：admin，Password：112233
    //body参数为
    /**
     * grant_type:password
     * username:admin
     * scope:all
     * password:123456
     */
    //拿到返回token令牌
    /**
     * {
     *     "access_token": "c02af830-72be-42db-92b4-1e163c5237d2",
     *     "token_type": "bearer",
     *     "expires_in": 43199,
     *     "scope": "all"
     * }
     */
    //用令牌去资源服务器获取用户信息
    //3、拿到令牌再用postman发送post请求http://localhost:8080/user/getCurrentUser，Authorization类型为bearer Token模式，Token为c02af830-72be-42db-92b4-1e163c5237d2
    //得到
    /**
     * {
     *     "username": "admin",
     *     "password": "$2a$10$ngW2P3jxjG4zn7NC7PSV5ewp4LZ1Yd13dD84uPOK/MSXmKeSOb2SS",
     *     "accountNonExpired": true,
     *     "accountNonLocked": true,
     *     "credentialsNonExpired": true,
     *     "authorities": [
     *         {
     *             "authority": "admin"
     *         }
     *     ],
     *     "enabled": true
     * }
     */
}
