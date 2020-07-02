package com.hwq.bean;

import com.sun.media.jfxmediaimpl.MediaDisposer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class InitBeanOrder implements InitializingBean, BeanPostProcessor, DisposableBean {

     public static volatile int num=1;

    public InitBeanOrder(){
        System.out.println("initBeanOrder constructor");
        printNum();
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("initBeanOrder postConstruct");
        printNum();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("initBeanOrder afterPropertiesSet");
        printNum();
    }

    public void initMethod(){
        System.out.println("initBeanOrder initMethod");
        printNum();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("initBeanOrder postProcessBeforeInitialization");
        printNum();
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("initBeanOrder postProcessAfterInitialization");
        printNum();
        return bean;
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("initBeanOrder preDestroy");
        printNum();
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("initBeanOrder destroy");
        printNum();
    }

    public void destoryMethod(){
        System.out.println("initBeanOrder destoryMethod");
        printNum();
    }


    private void printNum(){
        System.out.println("num当前值"+num+", 加1后等于"+ (++num));
    }
}
