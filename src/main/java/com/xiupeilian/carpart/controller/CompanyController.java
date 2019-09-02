package com.xiupeilian.carpart.controller;

import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("company")
public class CompanyController {
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PrimeService primeService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private PartsService partsService;

        @RequestMapping("companyManage")
        public String tocompanyManage(HttpServletRequest request){
            SysUser user= (SysUser) request.getSession().getAttribute("user");
            SysUser sysUser=userService.findUserByLoginName(user.getLoginName());

            Company company=companyService.findCompanyByCompanyId(sysUser.getCompanyId());
            Brand brand=brandService.findBrandById(company.getSingleBrand());
            Parts parts=brandService.findPartsById(company.getSingleParts());
            request.setAttribute("salebusiness",company);
            request.setAttribute("brand",brand.getChineseName());
            request.setAttribute("parts",parts.getName());
            return "login/salebusinessEdit";
        }
    }




