package com.xxxx.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码
 *
 * @author harry
 * @since 2022/11/13
 */
@RestController
public class CatpchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value="验证码")
    @GetMapping(value="/catpcha",produces = "image/jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        //设置请求头输出位image类型
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma", "No-cache");
        response.setContentType("image/jpg");

        //业务代码
        //生成验证码
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容："+text);
        request.getSession().setAttribute("captcha", text);
        BufferedImage image = defaultKaptcha.createImage(text);
//        ImageIO.write(image, "jpg", response.getOutputStream());
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流，输出图片
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null!=outputStream){
                try {
                    outputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
