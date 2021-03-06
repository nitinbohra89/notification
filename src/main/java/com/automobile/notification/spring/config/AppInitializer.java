package com.automobile.notification.spring.config;

import javax.servlet.Filter;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class[] getRootConfigClasses() {
		return new Class[] { AppConfig.class,DataBaseConfig.class,SecurityConfig.class };
	}

	@Override
	protected Class[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters(){
		return new Filter[]{new DelegatingFilterProxy("springSecurityFilterChain")};
	}

}
