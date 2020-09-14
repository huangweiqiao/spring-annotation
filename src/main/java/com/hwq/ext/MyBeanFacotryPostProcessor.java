package com.hwq.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * BeanFactoryPostProcessor 是spring提供的一个扩展接口，
 * 我们可以通过扩展这个接口来参与到 bean对象生成过程当中，例如可以修改bean的作用域等等
 */
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
