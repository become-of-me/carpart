package com.xiupeilian.carpart.controller;

import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * @Description: 登录注册
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 13:56
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSenderImpl mailSender;
    /**
     * @Description: 去往登录页面
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login/login";
    }

    @RequestMapping("/login")
    public void login(LoginVo vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
        //判断验证码是否正确
        String code=(String)request.getSession().getAttribute(SysConstant.VALIDATE_CODE);
        if(vo.getValidate().toUpperCase().equals(code.toUpperCase())){
            //验证码正确
            vo.setPassword(SHA1Util.encode(vo.getPassword()));
            SysUser user= userService.findUserByLoginNameAndPassword(vo);
            if(null==user){
                response.getWriter().write("2");
            }else{
                //登录成功
                request.getSession().setAttribute("user",user);
                response.getWriter().write("3");
            }
        }else{
            //验证码错误
            response.getWriter().write("1");
        }
    }
    @RequestMapping("feifa")
    public String feifa(){
        return "exception/feifa";
    }
    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "login/forgetPassword";
    }
    @RequestMapping("getPassword")
    public void getPassword(HttpServletResponse response,LoginVo vo) throws IOException {
        SysUser user=userService.findUserByLoginNameAndEmail(vo);
        if (null==user){
            response.getWriter().write("1");
        }else{
            //生成新密码
            String newpassword=new Random().nextInt(899999)+100000+"";
            //修改数据库
            user.setPassword(SHA1Util.encode(newpassword));
            userService.updateUser(user);
            //发送邮件到用户邮箱
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("15670975975@sina.cn");
            message.setTo(user.getEmail());
            message.setSubject("我来了");
            message.setText("您的新密码是"+newpassword);
            mailSender.send(message);
            response.getWriter().write("2");
        }
    }
}
