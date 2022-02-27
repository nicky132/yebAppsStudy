package com.xxxx.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */
@RestController
public class HelloController {
    @ApiOperation(value = "hello")
    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}
