package com.maxplus1.demo.config.shiro.interceptor;

import com.maxplus1.demo.config.shiro.rbac.AccountUtils;
import com.maxplus1.demo.config.shiro.rbac.User;
import org.apache.shiro.session.Session;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseController {

    /**
     * 获得响应
     * @return
     */
    public HttpServletRequest req(){
        return WebResource.request();
    }

    /**
     * 获得请求
     * @return
     */
    public HttpServletResponse res(){
        return WebResource.response();
    }

    /**
     * 获得web上下文
     * @return
     */
    public WebApplicationContext cxt() {
        return WebResource.webcontext();
    }

    /**
     * 获得会话
     * @return
     */
    public Session session() {
        return WebResource.session();
    }

    /**
     * 获取uri
     * @return
     */
    public String uri(){
        return req().getRequestURI();
    }

    /**
     * 获取当前登录用户userId
     * @return
     */
    public Long userId(){
        return AccountUtils.getUserId();
    }
//    public Long deptId(){
//        return user().getDeptId();
//    }

    public User user(){
        return AccountUtils.getUser();
    }


}

