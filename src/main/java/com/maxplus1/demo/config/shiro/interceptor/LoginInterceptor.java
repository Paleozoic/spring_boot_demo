package com.maxplus1.demo.config.shiro.interceptor;


import com.maxplus1.demo.config.shiro.rbac.AccountUtils;
import com.maxplus1.demo.config.shiro.rbac.service.IUserService;
import com.maxplus1.demo.config.shiro.rbac.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 登陆拦截器，存储一些账号信息进session
 * @author Paleo
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

    @Resource
    private IUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
            throws Exception {
        if(SecurityUtils.getSubject().isAuthenticated()){
            Long userId = (Long) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            AccountUtils.setUserId(userId);
            User userById = userService.getUserByNameWithoutPassword(userId);
            AccountUtils.setUser(userById);
        }
    }

}
