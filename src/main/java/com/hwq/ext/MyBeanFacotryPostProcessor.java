package com.hwq.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * BeanFactoryPostProcessor 是spring提供的一个扩展接口，针对beanFactory来建设,
 * 我们可以通过扩展这个接口来参与到 bean对象生成过程当中，例如可以修改bean的作用域等等
 *
 * 经典应用场景：
 * ConfigurationClassPostProcessor#postProcessBeanFactory  给java config类加上CGLIB代理
 *
 * ConfigurationClassPostProcessor这个类做了很多事情: 扫描, 三种import的扫描，@Bean扫描 判断配置类是否是一个完整的配置类（是否加@Configration）
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
