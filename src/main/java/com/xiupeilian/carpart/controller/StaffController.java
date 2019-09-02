package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.City;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.CityService;
import com.xiupeilian.carpart.service.CompanyService;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CityService cityService;
    /*@RequestMapping("/staffList")
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
    }*/

    @RequestMapping("/staffList")
    public String staffList(LoginVo vo, Integer pageNo,
                            Integer pageSize,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception{
        pageNo=pageNo==null?1:pageNo;
        pageSize=pageSize==null?9:pageSize;
        PageHelper.startPage(pageNo,pageSize);
        List<SysUser> userList = userService.findUser(vo);
        PageInfo<SysUser> page = new PageInfo<>(userList);
        request.setAttribute("page",page);
        request.setAttribute("staffList",userList);
        request.setAttribute("username",vo.getUsername());
        return "staff/staffList";
    }
    @RequestMapping("/toAddStaff")
    public String toAddStaff(){
        return "staff/addStaff";
    }
    @RequestMapping("/checkLoginName")
    public void checkLoginName(String loginName,HttpServletResponse response) throws Exception{
        SysUser user = userService.findUserByLoginName(loginName);
        if(null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }
    @RequestMapping("checkPhone")
    public void checkPhone(String telnum,HttpServletResponse response) throws Exception{
        SysUser user = userService.findUserByPhone(telnum);
        if(null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }
    @RequestMapping("checkEmail")
    public void checkEmail(String email,HttpServletResponse response) throws Exception{
        SysUser user = userService.findUserByPhone(email);
        if(null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }
    @RequestMapping("/addStaff")
    public String addStaff(SysUser user,HttpServletRequest request){
        SysUser loginUser = (SysUser) request.getSession().getAttribute("user");
        user.setRoleId(4);
        user.setCompanyId(loginUser.getCompanyId());
        user.setManageLevel(4);
        user.setCreateTime(new Date());
        userService.addUser(user);
        return "redirect:staffList";
    }
    @RequestMapping("/toEditStaff")
    public String toEditStaff(Integer id,HttpServletRequest request){
        SysUser user = userService.findUserById(id);
        Company company = companyService.findCompanyById(user.getCompanyId());
        List<City> cityList = cityService.findCities();
        request.setAttribute("company",company);
        request.setAttribute("cityList",cityList);
        request.setAttribute("user",user);
        return "staff/editStaff";
    }
    @RequestMapping("/editStaff")
    public String editStaff(Integer id,Integer userStatus){
        userService.updateUserStatusById(id,userStatus);
        return "redirect:staffList";
    }

}
