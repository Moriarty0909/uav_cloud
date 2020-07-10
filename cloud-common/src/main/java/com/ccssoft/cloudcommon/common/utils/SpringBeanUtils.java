package com.ccssoft.cloudcommon.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class SpringBeanUtils{
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static Object getBean (String beanName) {
        return applicationContext.getBean(beanName);
    }
}
