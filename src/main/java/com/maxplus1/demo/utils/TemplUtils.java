package com.maxplus1.demo.utils;


import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Slf4j
public class TemplUtils {

    public static Template loadTempl(String name, String templateContent) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate(name, templateContent);
        cfg.setTemplateLoader(stringTemplateLoader);
        cfg.setDefaultEncoding("UTF-8");
        Template template = null;
        try {
            template = cfg.getTemplate(name, "UTF-8");
        } catch (IOException e) {
            log.error("[ERROR===>>>]can not get template:" + e.getMessage(), e);
        }
        return template;
    }


    public static String processTempl(String name, String templateContent, Map<String, Object> params) {
        Template template = loadTempl(name, templateContent);
        Writer writer = new StringWriter();
        try {
            template.process(params, writer);
        } catch (TemplateException e) {
            log.error("[ERROR===>>>]process template error:" + e.getMessage(), e);
        } catch (IOException e) {
            log.error("[ERROR===>>>]process template error:" + e.getMessage(), e);
        }
        String result = writer.toString();
        try {
            writer.flush();
        } catch (IOException e) {
            log.error("[ERROR===>>>]StringWriter flush error:" + e.getMessage(), e);
        }
        return result;

    }
}
