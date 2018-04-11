package com.maxplus1.demo.config.xss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Autowired
    private XSSFilter xssFilter;
    @Bean
    public FilterRegistrationBean registrationXssFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(xssFilter);
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        return registration;
    }
}
