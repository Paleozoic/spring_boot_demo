package com.maxplus1.demo.config.shiro.rbac.service;

import com.maxplus1.demo.config.shiro.rbac.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{
    @Override
    public User getUserByNameWithPassword(String userName) {
        return null;
    }

    @Override
    public User getUserByNameWithoutPassword(Long userId) {
        return null;
    }
}
