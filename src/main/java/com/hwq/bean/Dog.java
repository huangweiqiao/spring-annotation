package com.hwq.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Dog(){
        System.out.println("dog constructor...");
    }

    //对象创建并复制之后调用
    @PostConstruct
    public void init(){
        System.out.println("dog PostConstruct...");
    }

    //容器移除对象前 执行
    @PreDestroy
    public void destory(){
        System.out.println("dog PreDestroy...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
