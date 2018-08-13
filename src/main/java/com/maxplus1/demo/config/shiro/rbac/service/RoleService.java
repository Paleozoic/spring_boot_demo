package com.maxplus1.demo.config.shiro.rbac.service;

import com.maxplus1.demo.config.shiro.rbac.Role;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleService implements  IRoleService{
    @Override
    public List<Role> getUserRoleList(Long userId) {
        return null;
    }
}
