package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;

import java.util.List;

/**
 * @Description: 用户表相关操作
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 15:04
 * @Version: 1.0
 **/
public interface UserService {
    public SysUser findUserByLoginNameAndPassword(LoginVo vo);

    List<Menu> findMenusById(int id);

    SysUser findUserByLoginNameAndEmail(LoginVo vo);

    void updateUser(SysUser user);
}
