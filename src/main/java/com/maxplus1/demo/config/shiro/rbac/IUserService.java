package com.maxplus1.demo.config.shiro.rbac;



public interface IUserService {


    User getUserByNameWithPassword(String userName);


    User getUserByNameWithoutPassword(Long userId);
}
