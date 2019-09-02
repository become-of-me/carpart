package com.xiupeilian.carpart.controller;

import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.service.CompanyService;
import com.xiupeilian.carpart.service.ItemsService;
import com.xiupeilian.carpart.util.AliyunOSSClientUtil;
import com.xiupeilian.carpart.vo.CompanyPictureVo;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/upLoad")
public class UpLoadControllter {
    @Autowired
    private ItemsService itemService;
    @Autowired
    private CompanyService companyService;
    @RequestMapping("/myUpload")
    public String upload(HttpServletRequest request) {
        //Items item=itemService.findItemByid(1);
        //request.setAttribute("imgurl",item.getPicUrl());

        return "upload/index";
    }

    @RequestMapping(value = "photoupload", method = {RequestMethod.POST, RequestMethod.GET})
    public void myphotoupload(HttpServletRequest request, @RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File f = fi.getStoreLocation();
        System.err.println(AliyunOSSClientUtil.uploadObject2OSS(AliyunOSSClientUtil.getOSSClient(), f, SysConstant.BACKET_NAME, SysConstant.FOLDER));
        //String url = AliyunOSSClientUtil.uploadObject2OSS(AliyunOSSClientUtil.getOSSClient(), f, SysConstant.BACKET_NAME, SysConstant.FOLDER);
        System.out.println(AliyunOSSClientUtil.getUrl((SysConstant.FOLDER+f.getName())));
        //System.out.println("图片的访问地址"+"https://"+SysConstant.BACKET_NAME+"."+SysConstant.ENDPOINT+"/"+SysConstant.FOLDER+f.getName());
        String imgUrl="https://"+SysConstant.BACKET_NAME+"."+SysConstant.ENDPOINT+"/"+SysConstant.FOLDER+f.getName();
        response.getWriter().write(AliyunOSSClientUtil.getUrl((SysConstant.FOLDER+f.getName())));

        System.out.println(imgUrl);


    }

    @RequestMapping("tocompanyUpload")
    public String toCompanyUpload(String picture,int companyid,HttpServletRequest request){

        request.setAttribute("pictureid",picture);
        request.setAttribute("companyid",companyid);
        return "login/index";
    }

    @RequestMapping("updateCompany")
    public void updateCompany(CompanyPictureVo vo, HttpServletResponse response) throws IOException {
        if (vo.getPictureid().equals("picurl1")){
            companyService.updateCompanyPictureById(vo);
            response.getWriter().write("1");
        }else if(vo.getPictureid().equals("picurl2")){
            companyService.updateCompanyPictureById2(vo);
            response.getWriter().write("1");
        }else {
            companyService.updateCompanyPictureById3(vo);
            response.getWriter().write("1");
        }

    }
    @RequestMapping("updatememo")
    public void updatememo(Company company, HttpServletResponse response) throws IOException {

        companyService.updateCompanyMemoById(company);
        response.getWriter().write("1");
    }
    @RequestMapping("updateCompanyInfo")
    public void updateCompanyInfo(Company company,HttpServletResponse response)throws Exception{
        companyService.updateByPrimaryKeySelective(company);
        response.getWriter().write("1");
    }
}
