package com.xxxx.jwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
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

    /**
     * 创建token（失效时间）
     */
    @Test
    public void testCreateTokenHasExp(){
        //当前系统时间
        long now  = System.currentTimeMillis();
        long exp = now + 60*1000;
        //创建jwt构建对象
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("8888")
                .setSubject("Rose")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"xxxx")
                .setExpiration(new Date(exp));
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

    /**
     * 解析token(失效时间)
     */
    @Test
    public void testParseTokenHasExp(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTY0NTg0NzE4NiwiZXhwIjoxNjQ1ODQ3MjQ2fQ.xjn5-sX42i925UUGQIKA11KzEhoLED4LkUQOV7U9Ml4";
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("subject:"+claims.getIssuedAt());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间："+simpleDateFormat.format(claims.getIssuedAt()));
        System.out.println("过期时间："+simpleDateFormat.format(claims.getExpiration()));
        System.out.println("当前时间："+simpleDateFormat.format(new Date()));
    }
}
