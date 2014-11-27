package com.browniebytes.wow.addonmanager.util;

import javafx.util.Callback;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppControllerFactory implements Callback<Class<?>, Object>, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object call(Class<?> aClass) {
        return applicationContext.getBean(aClass);
    }
}
