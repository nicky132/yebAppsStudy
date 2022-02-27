package com.xxxx.jwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class JwtdemoApplicationTests {
    //创建token
    @Test
    public void testCreateToken() {
        //创建jwt构建对象
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("8888")
                .setSubject("Rose")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"xxxx");
        //获取jwttoken
        String token = jwtBuilder.compact();
        System.out.println(token);

        System.out.println("============================");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }
    //解析token
    @Test
    public void testParseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTY0NTg0NjA3MX0.on2akvXT3fsx_7rxR8mgBsnF9rz9jgBMRtIcddqaDr4";
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("subject:"+claims.getIssuedAt());
    }

}
