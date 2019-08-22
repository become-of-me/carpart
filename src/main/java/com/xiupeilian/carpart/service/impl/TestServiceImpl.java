package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.SysUserMapper;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 9:46
 * @Version: 1.0
 **/
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser findUserById(int id){
       return sysUserMapper.selectByPrimaryKey(id);
    }
}
