package com.hwq;

import com.hwq.bean.Person;
import com.hwq.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        //这就是IOC容器
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = annotationConfigApplicationContext.getBean(Person.class);
        /**
         * 这里就看到能打印出person对象了，其实上面的两行代码和以前用配置文件的时候的如下代码一样
         * ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
         * Person bean = classPathXmlApplicationContext.getBean(Person.class);
         */
        System.out.println(person);

        //查看person这个组件在IOC容器中的名字是什么,默认名字就是方法名，但是可以通过 @Bean 注解中的 name属性指定
        String[] beanNamesForAnnotation = annotationConfigApplicationContext.getBeanNamesForType(Person.class);
        for (String name:beanNamesForAnnotation){
            System.out.println(name);
        }


        for (String beanDefinitionName : annotationConfigApplicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

    }
}
