package com.hwq.test;

import com.hwq.bean.Blue;
import com.hwq.bean.Person;
import com.hwq.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.Map;

public class IOCTest {

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @Test
    public void test01(){
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String name:beanDefinitionNames){
            System.out.println(name);
        }
    }

    @Test
    public void test02(){

        System.out.println("容器创建结束.....");
        Object person = annotationConfigApplicationContext.getBean("person");
        Object person2 = annotationConfigApplicationContext.getBean("person");
        System.out.println(person == person2);
    }

    @Test
    public void test03(){
        //获取环境变量值
        ConfigurableEnvironment environment = annotationConfigApplicationContext.getEnvironment();
        System.out.println("操作系统:"+environment.getProperty("os.name"));

        String[] beanNamesForTypes = annotationConfigApplicationContext.getBeanNamesForType(Person.class);

        Arrays.stream(beanNamesForTypes).forEach(x->{
            System.out.println(x);
        });

        Map<String, Person> beansOfType = annotationConfigApplicationContext.getBeansOfType(Person.class);
        beansOfType.forEach((k,v)->{
            System.out.println(k+"-->"+v);
        });
    }

    @Test
    public void testImport(){
        printBeans();
        Blue bean = annotationConfigApplicationContext.getBean(Blue.class) ;
        System.out.println(bean.getClass().getSimpleName());

        //这里虽然用的是工厂bean的名字去获取对象，但是返回的其实是 工厂bean对象的getObject返回的对象，所以这里是Color对象
        Object colorFactoryBean = annotationConfigApplicationContext.getBean("colorFactoryBean");
        System.out.println(colorFactoryBean);
        //但是我就是想得到这个工厂bean对象,那可以在bean id前加&
        Object colorFactoryBean2 = annotationConfigApplicationContext.getBean("&colorFactoryBean");
        System.out.println(colorFactoryBean2);


    }

    public void printBeans(){
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(x->{
            System.out.println(x);
        });
    }
}
