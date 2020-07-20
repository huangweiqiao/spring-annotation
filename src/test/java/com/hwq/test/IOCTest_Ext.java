package com.hwq.test;

import com.hwq.bean.Blue;
import com.hwq.bean.Person;
import com.hwq.config.MainConfig2;
import com.hwq.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.Map;

public class IOCTest_Ext {



    @Test
    public void test01(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        annotationConfigApplicationContext.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
        });
        annotationConfigApplicationContext.close();
    }
}
