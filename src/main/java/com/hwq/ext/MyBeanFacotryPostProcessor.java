package com.hwq.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class MyBeanFacotryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFacotryPostProcessor.postProcessBeanFactory");
        int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
        String [] names = beanFactory.getBeanDefinitionNames();
        System.out.println("当前beanFactory中有"+beanDefinitionCount+"个bean定义");
        Stream.of(names).forEach(n-> System.out.println(n));
    }

}
