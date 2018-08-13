package com.maxplus1.demo.config.shiro.interceptor;


import com.maxplus1.demo.config.shiro.rbac.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 登陆拦截器，存储一些账号信息进session
 * @author Paleo
 *
 */
@Component
public class AccessInterceptor implements HandlerInterceptor{

    @Autowired
    private IMenuService menuService;



    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        //TODO 算了 使用RequiresPermissions实现算了 因为涉及动态url  这里不好处理
//        String requestURI = req.getRequestURI();
//        List<Menu> userMenus =menuService.getUserMenuList(AccountUtils.getUserId());
//        //将requestURI转化为menuCode
//        for (Menu menu : userMenus) {
//            String url = menu.getUrl();
//            if(url!=null && url.contains())
//
//        }
//        return  SecurityUtils.getSubject().isPermitted()[0];
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

    }

}
