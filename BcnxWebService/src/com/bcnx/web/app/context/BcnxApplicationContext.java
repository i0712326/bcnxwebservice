package com.bcnx.web.app.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BcnxApplicationContext implements ApplicationContextAware {
	private static ApplicationContext bcnxContext;
	private BcnxApplicationContext() {}
	@Override
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		bcnxContext = appContext;
	}
	public static Object getBean(String beanName) {
		return bcnxContext.getBean(beanName);
	}
}
