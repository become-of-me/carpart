package com.xiupeilian.carpart.controller;

import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 9:43
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @RequestMapping("/test")
    public @ResponseBody
    SysUser test(int id){
        return testService.findUserById(id);
    }
}
