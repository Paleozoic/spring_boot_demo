package com.maxplus1.demo.config.shiro;

import com.maxplus1.demo.config.shiro.interceptor.AccessInterceptor;
import com.maxplus1.demo.config.shiro.interceptor.LoginInterceptor;
import com.maxplus1.demo.config.shiro.interceptor.RequestResourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Created by qxloo on 2017/9/5.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private AccessInterceptor accessInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //web资源拦截器，通过此拦截器将request,response等存进ThreadLocal
        registry.addInterceptor(new RequestResourceInterceptor()).addPathPatterns("/**");
        //登陆拦截器，存储一些账号信息进session
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/ficc/login/login");
        //权限拦截器
//        registry.addInterceptor(accessInterceptor).addPathPatterns("/**")/*.excludePathPatterns()*/;
        super.addInterceptors(registry);
    }


    @Autowired
    private FreeMarkerViewResolver freeMarkerViewResolver;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(freeMarkerViewResolver);
        super.configureViewResolvers(registry);
    }



}
