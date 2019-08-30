package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/22 11:57
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private UserService userService;

    @RequestMapping("/staffList")
    public String staffList(Integer pageSize, Integer pageNum, HttpServletRequest request) throws Exception {

        pageSize = pageSize == null ? 10 : pageSize;
        pageNum = pageNum == null ? 1 : pageNum;
        PageHelper.startPage(pageNum, pageSize);
        //查询全部
        List<SysUser> count = userService.findAllUser();
        PageInfo<SysUser> page = new PageInfo<>(count);
        request.setAttribute("staffList", page.getList());
        request.setAttribute("page", page);
        return "index/staffList";
    }

    @RequestMapping("chazhao")
    public String findSysUserByUserName(HttpServletResponse response,HttpServletRequest request) throws  Exception{
        String username=request.getParameter("loginName");
        SysUser sysUser=new SysUser();
        sysUser.setLoginName(username);
        List<SysUser> list = new ArrayList<>();
        list = userService.findSysUserByUserName(sysUser);
        request.setAttribute("staffList",list);
        request.setAttribute("usern",username);
        return "index/staffList";
    }

}
