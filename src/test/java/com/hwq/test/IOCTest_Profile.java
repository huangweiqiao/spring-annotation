package com.hwq.test;

import com.hwq.config.MainConfigOfAutowired;
import com.hwq.config.MainConfigOfProfile;
import com.hwq.dao.BookDao;
import com.hwq.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class IOCTest_Profile {

    //1、使用命令行动态参数切换环境 (run configurations-->VM arguments-->加上 -Dspring.profiles.active=test)
    @Test
    public void test01(){
        AnnotationConfigApplicationContext configApplicationContext =
                new AnnotationConfigApplicationContext(MainConfigOfProfile.class);

        String[] beanNamesForType = configApplicationContext.getBeanNamesForType(DataSource.class);
        for (String name:beanNamesForType){
            System.out.println(name);
        }

        configApplicationContext.close();
    }

    //2、 通过代码设置环境
    @Test
    public void test02(){
        //1、创建一个applicationContext,要通过代码设置环境，生成IOC容器的时候就不能用有参构造器了，只能用无参构造器
        AnnotationConfigApplicationContext configApplicationContext =
                new AnnotationConfigApplicationContext();
        //2、设置需要激活的环境
        configApplicationContext.getEnvironment().setActiveProfiles("dev"); //可以设置多个环境
        //3、注册主配置类
        configApplicationContext.register(MainConfigOfProfile.class);
        //4、 启动刷新容器
        configApplicationContext.refresh();

        String[] beanNamesForType = configApplicationContext.getBeanNamesForType(DataSource.class);
        for (String name:beanNamesForType){
            System.out.println(name);
        }

        configApplicationContext.close();
    }

}
