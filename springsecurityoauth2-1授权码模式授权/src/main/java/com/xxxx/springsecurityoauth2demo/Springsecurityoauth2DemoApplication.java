package com.xxxx.springsecurityoauth2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springsecurityoauth2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springsecurityoauth2DemoApplication.class, args);
        // http://localhost:8080/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all
        //1、用这个url拿授权码
        //https://www.baidu.com/?code=XCqo9H
        //2、再用code在postman发送post请求拿token令牌
        //http://localhost:8080/oauth/token ，
        // Authorization类型为basic auth模式，Username：admin，Password：112233
        //body为
        /**
         *         grant_type:authorization_code
         *         code:XCqo9H
         *         cliend_id:admin
         *         redirect_uri:http://www.baidu.com
         *         scope:all
         */
        //发送请求得到
        /**
         * {
         *     "access_token": "3a91063b-adfe-4421-9304-de5a109bc552",
         *     "token_type": "bearer",
         *     "expires_in": 43199,
         *     "scope": "all"
         * }
         */
        //3、拿到令牌再用postman发送get请求http://localhost:8080/user/getCurrentUser，Authorization类型为bearer Token模式，Token为3a91063b-adfe-4421-9304-de5a109bc552
        //得到
        /**
         * {
         *     "username": "admin",
         *     "password": "$2a$10$3Zj9Iv3LuyQkzdHb9JZJreTxCpnGFaYo33UiHmRrqF92E45ShgfGC",
         *     "enabled": true,
         *     "authorities": [
         *         {
         *             "authority": "admin"
         *         }
         *     ],
         *     "accountNonExpired": true,
         *     "accountNonLocked": true,
         *     "credentialsNonExpired": true
         * }
         */


    }

}
