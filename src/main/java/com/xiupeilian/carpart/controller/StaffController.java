package com.xiupeilian.carpart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/22 11:57
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/staff")
public class StaffController {
    @RequestMapping("/staffList")
    public void staffList(HttpServletResponse response)throws Exception{
        response.getWriter().write("normal success");
    }
}
