package com.hwq.test;

import com.hwq.bean.InitBeanOrder;
import com.hwq.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_LifeCycle {

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);

    @Test
    public void test01(){
        InitBeanOrder initBeanOrder = (InitBeanOrder)annotationConfigApplicationContext.getBean("initBeanOrder");
        initBeanOrder.showPrototyUser();
        initBeanOrder.showPrototyUser();
        annotationConfigApplicationContext.close();
    }

}
