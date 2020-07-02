package com.hwq.config;

import com.hwq.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//使用@PropertySource读取外部配置文件种的k/v保存到运行的环境变量中;加载完外部的配置文件后使用${}取的配置值
//相当于xml文件中的 <context:property-placeholder location="classpath:person.properties"/>
@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class MainConfigOfPropertyValues {

    @Bean
    public Person person(){
        return new Person();
    }
}
