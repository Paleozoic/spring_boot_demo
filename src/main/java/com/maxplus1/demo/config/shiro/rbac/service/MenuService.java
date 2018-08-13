package com.maxplus1.demo.config.shiro.rbac.service;

import com.maxplus1.demo.config.shiro.rbac.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService implements IMenuService{
    @Override
    public List<Menu> getMenuList() {
        return null;
    }

    @Override
    public List<Menu> getUserMenuList(Long userId) {
        return null;
    }
}
