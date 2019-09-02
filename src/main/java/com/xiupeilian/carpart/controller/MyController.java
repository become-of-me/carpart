package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.ItemsSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/my")
public class MyController {

    @Autowired
    private ItemsSerivce itemsSerivce;
    @Autowired
    private BrandService brandService;
    @RequestMapping("/myItems")
    public String myItems(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response){
        //初始化页面
        pageSize=pageSize==null?8:pageSize;
        pageNo=pageNo==null?1:pageNo;
        //获取当前登录用户
        SysUser sysUser=(SysUser) request.getSession().getAttribute("user");
        PageHelper.startPage(pageNo,pageSize);
        List<Items> itemsList=itemsSerivce.findItemsByUserId(sysUser.getId());


        PageInfo<Items> page=new PageInfo<>(itemsList);
        request.setAttribute("page",page);
        request.setAttribute("itemsList",itemsList);
        request.setAttribute("totalRows",page.getTotal());
        return "my/myCommodity";
    }
    @RequestMapping("/toAddPrime")
    public String toAddPrimes(HttpServletRequest request){
        System.out.println(12121212);
         List<Parts> partsList=brandService.findPartsAll();
        request.setAttribute("primeList",partsList);

        return "my/addPrime";
    }
    @RequestMapping("/deleteItemById")
    public void deleteItemById(Integer id,String picUrl){
        System.out.println("删除删除");
        itemsSerivce.deleteItemById(id);
       // Endpoint以杭州为例，其它Region请按实际情况填写。
     /*   String endpoint = "http://oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = SysConstant.ACCESS_KEY_ID;
        String accessKeySecret = SysConstant.ACCESS_KEY_SECRET;
        String bucketName = SysConstant.BACKET_NAME;
        String objectName = "ryb/img/"+picUrl;

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
     //   OSSClient ossClient = new OSSClient().build(endpoint, accessKeyId, accessKeySecret);
// 删除文件。
        ossClient.deleteObject(bucketName, objectName);

// 关闭OSSClient。
        ossClient.shutdown();*/
    }
    @RequestMapping(value = "/addPrime", method = {RequestMethod.POST, RequestMethod.GET})
    public void addPrime(HttpServletRequest  request,Items items,@RequestParam("file") MultipartFile file,HttpServletResponse response) throws IOException {
        SysUser user=(SysUser) request.getSession().getAttribute("user");
        items.setUserId(user.getId());
        itemsSerivce.addItem(items,file);
      //  System.out.println(imgUrl);
        response.sendRedirect(request.getContextPath()+"/my/close");
    }
    @RequestMapping("/toEdit")
    public String toEdit(Integer id,HttpServletRequest request){
        System.out.println("122121");
        Items items=itemsSerivce.findItemById(id);
        request.setAttribute("sale",items);
        return "my/editCommodity";
    }
    @RequestMapping(value = "/editCommodity", method = {RequestMethod.POST, RequestMethod.GET})
    public void editCommodity(Items items,@RequestParam("file") MultipartFile file,HttpServletResponse response,HttpServletRequest request) throws IOException {
            System.out.println("修改修改");
        itemsSerivce.updateItemById(items,file);
        response.sendRedirect(request.getContextPath()+"/my/close");
    }
    @RequestMapping("/close")
    public String close(){
        return "index/closeLayer";
    }

}
