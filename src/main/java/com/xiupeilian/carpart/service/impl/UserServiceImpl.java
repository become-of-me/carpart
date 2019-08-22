package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.MenuMapper;
import com.xiupeilian.carpart.mapper.SysUserMapper;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 15:06
 * @Version: 1.0
 **/
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private MenuMapper  menuMapper;


    @Override
    public SysUser findUserByLoginNameAndPassword(LoginVo vo) {
        return userMapper.findUserByLoginNameAndPassword(vo);
    }

    @Override
    public List<Menu> findMenusById(int id) {
        return menuMapper.findMenusByUserId(id);
    }

    @Override
    public SysUser findUserByLoginNameAndEmail(LoginVo vo) {
        return userMapper.findUserByLoginNameAndEmail(vo);
    }

    @Override
    public void updateUser(SysUser user) {
        userMapper.updateUser(user);
    }
}
