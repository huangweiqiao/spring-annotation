package com.hwq.test;

import com.hwq.bean.Person;
import com.hwq.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

public class IOCTest_PropertyValue {
    private AnnotationConfigApplicationContext configApplicationContext =
            new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);

    @Test
    public void test01(){
        printBeans();
        System.out.println("=====================================");


        Person person = configApplicationContext.getBean(Person.class);
        System.out.println(person);

        //这样也可以取到配置文件中的值
        ConfigurableEnvironment environment = configApplicationContext.getEnvironment();
        String property = environment.getProperty("person.nickName");
        System.out.println(property);

        configApplicationContext.close();
    }

    public void printBeans(){
        String[] beanDefinitionNames = configApplicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(x->{
            System.out.println(x);
        });
    }
}
