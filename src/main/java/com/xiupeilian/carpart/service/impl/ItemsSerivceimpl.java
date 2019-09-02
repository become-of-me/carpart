package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.mapper.ItemsMapper;
import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.service.ItemsSerivce;
import com.xiupeilian.carpart.util.AliyunOSSClientUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
@Service
public class ItemsSerivceimpl implements ItemsSerivce {
    @Autowired
    private ItemsMapper itemsMapper;
    @Override
    @Cacheable(value = "cache")
    public List<Items> findItemsByQueryVo(Items items) {
        return itemsMapper.findItemsByQueryVo(items);
    }

    @Override
    public List<Items> findItemsByUserId(Integer id) {
        return itemsMapper.findItemsByUserId(id);
    }

    @Override
    public void deleteItemById(Integer id) {
        itemsMapper.updateItemDeleteStatusById(id);
    }

    @Override
    public void addItem(Items items, MultipartFile file) {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File f = fi.getStoreLocation();
        System.err.println(AliyunOSSClientUtil.uploadObject2OSS(AliyunOSSClientUtil.getOSSClient(), f, SysConstant.BACKET_NAME, SysConstant.FOLDER));
        String imgUrl="https://"+SysConstant.BACKET_NAME+"."+SysConstant.ENDPOINT+"/"+SysConstant.FOLDER+f.getName();

        items.setPicUrl(AliyunOSSClientUtil.getUrl((SysConstant.FOLDER+f.getName())));
        items.setUpdateTime(new Date());
        itemsMapper.insertSelective(items);
    }

    @Override
    public Items findItemById(Integer id) {
        return itemsMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateItemById(Items items, MultipartFile file) {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File f = fi.getStoreLocation();
        System.err.println(AliyunOSSClientUtil.uploadObject2OSS(AliyunOSSClientUtil.getOSSClient(), f, SysConstant.BACKET_NAME, SysConstant.FOLDER));
        String imgUrl="https://"+SysConstant.BACKET_NAME+"."+SysConstant.ENDPOINT+"/"+SysConstant.FOLDER+f.getName();

        items.setPicUrl(AliyunOSSClientUtil.getUrl((SysConstant.FOLDER+f.getName())));
        items.setUpdateTime(new Date());
        itemsMapper.updateByPrimaryKeySelective(items);
    }
}
