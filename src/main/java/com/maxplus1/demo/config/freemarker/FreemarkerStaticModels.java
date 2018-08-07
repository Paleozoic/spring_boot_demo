package com.maxplus1.demo.config.freemarker;


import com.maxplus1.demo.utils.IDUtils;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class FreemarkerStaticModels extends HashMap<String, Object> {

	private static FreemarkerStaticModels FREEMARKER_STATIC_MODELS;

	private FreemarkerStaticModels() {

	}

	public static FreemarkerStaticModels getInstance() {
		if (FREEMARKER_STATIC_MODELS == null) {
			FREEMARKER_STATIC_MODELS = new FreemarkerStaticModels();
			// 初始化
			/**
			 * freemarker通过设置TemplateHashModel，可在前端模板直接调用Java静态方法。
			 */
			FREEMARKER_STATIC_MODELS.put(IDUtils.class.getSimpleName(), useStaticPackage(IDUtils.class.getName()));
		}
		return FREEMARKER_STATIC_MODELS;
	}

	public static TemplateHashModel useStaticPackage(String packageName) {
		try {
			BeansWrapper wrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_0).build();
			TemplateHashModel staticModels = wrapper.getStaticModels();
			TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageName);
			return fileStatics;
		} catch (Exception e) {
			log.error("[ERROR===>>>]"+e.getMessage(),e);
		}
		return null;
	}
}
