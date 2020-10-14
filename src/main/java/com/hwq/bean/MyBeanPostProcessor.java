package com.hwq.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 初始化前后进行处理，是spring提供的一个扩展接口，，用来让我们可以插手bean实例过程。
 * 在spring中的任一一个bean放入容器之前可以回调 BeanPostProcessor 的方法，所以也叫初始化方法回调
 * BeanPostProcessor 的经典应用：
 * @PostConstruct  这个注解的功能就是通过 CommonAnnotationBeanPostProcessor来实现的
 * aop
 *
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+" postProcessBeforeInitialization...");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+" postProcessAfterInitialization...");
        return bean;
    }
}
