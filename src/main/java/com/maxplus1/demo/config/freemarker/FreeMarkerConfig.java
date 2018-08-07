package com.maxplus1.demo.config.freemarker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;

/**
 * Created by qxloo on 2017/9/4.
 */
@Configuration
public class FreeMarkerConfig {
    @Autowired
    private FreeMarkerViewResolver freeMarkerViewResolver;

    @PostConstruct
    public void setStaticModels() {
        freeMarkerViewResolver.setAttributesMap(FreemarkerStaticModels.getInstance());
    }

}
