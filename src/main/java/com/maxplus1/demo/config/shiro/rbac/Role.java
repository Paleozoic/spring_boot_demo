package com.maxplus1.demo.config.shiro.rbac;

import lombok.Data;

@Data
public class Role {
    private Long roleId;
    private String roleName;
    private Long roleGroupId;
    private String roleGroupName;
    private Integer status;

}

