package com.maxplus1.demo.config.shiro.rbac;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String userName;
    private Long deptId;
    private String deptName;
    private String realName;
    private Integer status;
    private String password;


}
