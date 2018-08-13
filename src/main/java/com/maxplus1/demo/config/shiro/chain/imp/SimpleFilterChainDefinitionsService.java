package com.maxplus1.demo.config.shiro.chain.imp;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import com.maxplus1.demo.config.shiro.chain.remote.AbstractFilterChainDefinitionsService;
import com.maxplus1.demo.config.shiro.rbac.service.IMenuService;
import com.maxplus1.demo.config.shiro.rbac.Menu;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;


/**
 * 简单实现shiro过滤链动态加载，来源从数据库中加载
 *
 * @author HyDe Paleo修改于 2015-12
 *
 */
@Service
public class SimpleFilterChainDefinitionsService extends AbstractFilterChainDefinitionsService {

    @Resource
    private IMenuService menuService;

    /**
     * 实现父类方法，从资源表中取出数据，并封装成shiro过滤器可以加载的对像
     */
    @Override
    public LinkedHashMap<String, String> initOtherPermission() {
        List<Menu> menuList = menuService.getMenuList();
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (Menu menu : menuList) {
            if(StringUtils.isNotBlank(menu.getUrl())){
                map.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING,(String)menu.getMenuCode()));
            }
        }
        return map;
    }

}
