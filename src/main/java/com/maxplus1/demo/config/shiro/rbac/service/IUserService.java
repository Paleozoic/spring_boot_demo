package com.maxplus1.demo.config.shiro.rbac.service;


import com.maxplus1.demo.config.shiro.rbac.User;

public interface IUserService {


    User getUserByNameWithPassword(String userName);


    User getUserByNameWithoutPassword(Long userId);
}
