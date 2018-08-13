package com.maxplus1.demo.config.shiro.rbac.service;

import com.maxplus1.demo.config.shiro.rbac.Menu;

import java.util.List;


public interface IMenuService {
    /**
     * 获取菜单列表
     * @return
     */
    List<Menu> getMenuList();



    /**
     * 获取用户已被授权的菜单
     * @param userId
     * @return
     */
    List<Menu> getUserMenuList(Long userId);


}
