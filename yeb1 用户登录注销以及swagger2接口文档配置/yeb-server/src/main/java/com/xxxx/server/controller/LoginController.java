package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminLoginParam;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminRoleService;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录
 */
@RestController
@Api(tags = "LoginController")
public class LoginController {
    /**
     * 注入service
     */
    @Autowired
    private IAdminService adminService;
    @ApiOperation(value = "登录之后返回token")
    @CrossOrigin(origins = "http://47.114.150.30",maxAge = 3600)
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
       return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),request);
    }

    @ApiOperation(value="获取当前用户的信息")
    @CrossOrigin(origins = "http://47.114.150.30",maxAge = 3600)
    @GetMapping("/admin/info")
    public RespBean getAdmin(Principal principal){
        return adminService.getAdmin(principal);
    }

    @ApiOperation(value="退出登录")
    @CrossOrigin(origins = "http://47.114.150.30",maxAge = 3600)
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success(true,"注销成功！");
    }
}
