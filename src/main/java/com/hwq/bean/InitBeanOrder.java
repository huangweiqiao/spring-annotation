package com.hwq.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class InitBeanOrder implements InitializingBean, DisposableBean {

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


    public void showPrototyUser(){
        PrototyUser user = getUser();
        System.out.println(user.hashCode());
    }

    //因为该类是单例，但是PrototyUser是原型，如果想在单例对象里使用原型对象（每次使用不同的对象），则可以像该方法一下获取原型对象
    //用@Lookup修饰了的方法，spring会根据返回类型去实例化对象
    @Lookup
    public PrototyUser getUser(){
        return null;
    }

    private void printNum(){
        System.out.println("num当前值"+num+", 加1后等于"+ (++num));
    }
}
