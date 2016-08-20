package com.cargocn.web.freemarker;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.TemplateException;

public class ShiroTagFreeMarkerConfigurer extends FreeMarkerConfigurer {
	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
		super.afterPropertiesSet();
		this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
	}

	@Override
	public TaglibFactory getTaglibFactory() {
		TaglibFactory tagLibFactory = super.getTaglibFactory();
		if (tagLibFactory.getObjectWrapper() == null) {
			tagLibFactory.setObjectWrapper(super.getConfiguration().getObjectWrapper());
		}
		return tagLibFactory;
	}
}
