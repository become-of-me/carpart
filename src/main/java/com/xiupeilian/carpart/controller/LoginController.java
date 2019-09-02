package com.xiupeilian.carpart.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.*;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.CityService;
import com.xiupeilian.carpart.service.CompanyService;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.task.MailTask;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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
    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CityService cityService;
    @Autowired
    private RedisTemplate jedis;
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
      /*  String code=(String)request.getSession().getAttribute(SysConstant.VALIDATE_CODE);
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
        }*/
        //判断验证码是否正确
        String code=(String)request.getSession().getAttribute(SysConstant.VALIDATE_CODE);
        if(vo.getValidate().toUpperCase().equals(code.toUpperCase())){
            //验证码正确
            Subject subject= SecurityUtils.getSubject();
            UsernamePasswordToken token=new UsernamePasswordToken(vo.getLoginName(),vo.getPassword());
            try {
                subject.login(token);
            }catch (Exception e){
                //用户名密码错误
                response.getWriter().write(e.getMessage());
                return ;
            }
            //获取存入的用户信息
            SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
            //存spring-session
            request.getSession().setAttribute("user",user);
            response.getWriter().write("3");
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
            /**
             创建一个任务，交给线程池就可以了.
             **/
            MailTask mailTask=new MailTask(mailSender,message);
            //让线程池去执行该任务就可以了
            executor.execute(mailTask);
            response.getWriter().write("2");
        }
    }
    @RequestMapping("toRegister")
    public String toRegister(HttpServletRequest request){
        //初始化数据  汽车品牌、配件种类、精品种类
        List<Brand> brandList=brandService.findBrandAll();
        List<Parts> partsList=brandService.findPartsAll();
        List<Parts> primelist=brandService.findPrimeAll();
        request.setAttribute("brandList",brandList);
        request.setAttribute("partsList",partsList);
        request.setAttribute("primeList",primelist);
        return "login/register";
    }
    @RequestMapping("getCity")
    public @ResponseBody List<City> getCity(Integer parentId){
        parentId=parentId==null?SysConstant.CITY_CHINA_ID:parentId;
        List<City> cityList=cityService.findCitiesByParentId(parentId);
        return cityList;
    }
    @RequestMapping("checkLoginName")
    public void checkLoginName(String loginName,HttpServletResponse response)throws  Exception{
        SysUser user=userService.findUserByLoginName(loginName);
        if (null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }
    @RequestMapping("checkPhone")
    public void checkPhone(String telnum,HttpServletResponse response)throws  Exception{
        SysUser user=userService.findUserByPhone(telnum);
        if (null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }

    @RequestMapping("checkEmail")
    public void checkEmail(String email,HttpServletResponse response)throws  Exception{
        SysUser user=userService.findUserByEmail(email);
        if (null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }
    @RequestMapping("checkCompanyname")
    public void checkCompanyname(String companyname,HttpServletResponse response)throws  Exception{
        Company company =companyService.findCompanyByCompanyName(companyname);
        if (null==company){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }


    @RequestMapping("/register")
    public String register(RegisterVo vo){

        userService.addRegsiter(vo);
        return "redirect:toLogin";
    }
    @RequestMapping("smsControllter")

    public void smsControllter(String phone, HttpServletResponse responses){

        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIQvSPB5YICXD9", "W5SjRWDlYTABmUrNVLnugNCvX85Fm5");
        IAcsClient client = new DefaultAcsClient(profile);
        int code=  (int)((Math.random()*9+1)*100000);
        String str="{'la1234':"+code+"}";
        String codes=code+"";
        jedis.opsForValue().set(phone,codes);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "白芥子");
        request.putQueryParameter("TemplateCode", "SMS_172884042");
        request.putQueryParameter("TemplateParam", str);

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }



    }

@RequestMapping("sms")
    public String sms(){
        return "login/sms";
}
@RequestMapping("smsQuery")
public void smsQuery(HttpServletResponse response,String phone) throws IOException {

             Object code= jedis.opsForValue().get(phone);
             System.out.println(code.toString());

             if (code!=null){
                 response.getWriter().write(2);

             }else{
              response.getWriter().write(3);

    }




}
    @RequestMapping("/message")
    public void message(String telnum){

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIstYYIyKChDvT", "lrnzLPgS4RFYybDR48BYNZUAvtAHVr");
        IAcsClient client = new DefaultAcsClient(profile);

        String code=new Random().nextInt(899999)+100000+"";

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", telnum);
        request.putQueryParameter("SignName", "\u4fee\u914d\u8fde");
        request.putQueryParameter("TemplateCode", "SMS_172889045");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        jedis.boundValueOps(telnum).set(code,60000);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/Verification")
    public void Verification(String Verification,String telnum,HttpServletResponse response) throws IOException {

        String aa= (String) jedis.opsForValue().get(telnum);
        if (aa.equals(Verification)){
            response.getWriter().print("1");
        }else {
            response.getWriter().print("2");
        }


    }

}
