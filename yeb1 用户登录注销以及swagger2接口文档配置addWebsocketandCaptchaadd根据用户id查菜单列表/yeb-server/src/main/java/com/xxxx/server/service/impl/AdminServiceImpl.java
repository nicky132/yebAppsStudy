package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.config.security.JwtTokenUtil;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cdh
 * @since 2022-02-12
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
         String captcha;
        captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error(false,"验证码输入错误，请重新输入！");
        }
        /* 登录 */
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(null == userDetails || !passwordEncoder.matches(password,userDetails.getPassword())){
           return RespBean.error(false,"用户名或密码不正确");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error(false,"账号被禁用，请联系管理员！");
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success(true,"登录成功",tokenMap);
    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        //去查数据库表，查用户被启用，且没被禁用
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }

    /**
     * 根据用户名获取
     * @param principal
     * @return
     */
    @Override
    public RespBean getAdmin(Principal principal) {
        if(null == principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = getAdminByUserName(username);
        admin.setPassword(null);
        return RespBean.success(true,"获取成功",admin);
    }

}
