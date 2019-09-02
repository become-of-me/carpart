package com.xiupeilian.carpart.vo;

/**
 * @Description: 修改企业图片
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/31 19:02
 * @Version: 1.0
 **/
public class CompanyPictureVo {
    private String data;
    private String pictureid;
    private Integer companyid;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPictureid() {
        return pictureid;
    }

    public void setPictureid(String pictureid) {
        this.pictureid = pictureid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }
}
