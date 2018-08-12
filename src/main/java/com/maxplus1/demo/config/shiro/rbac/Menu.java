package com.maxplus1.demo.config.shiro.rbac;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Menu {
    private Long menuId;
    private String menuCode;
    private Long upperMenuId;
    private String upperMenuName;
    private String menuName;
    private String type;
    private Integer orderNo;
    private String url;
    private Integer status;
    private String appName;

}
