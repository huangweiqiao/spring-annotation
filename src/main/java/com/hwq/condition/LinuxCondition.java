package com.hwq.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否linux系统
 */
public class LinuxCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取到IOC使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //获取类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        //获取当前环境信息
        Environment environment = conditionContext.getEnvironment();
        //获取bean定义的注册信息
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
//        registry.containsBeanDefinition("");//容器中是否包含某个bean
//        registry.registerBeanDefinition(); //注册bean
        //是否liunx系统
        String osname =environment.getProperty("os.name");
        if (osname.contains("linux")){
            return true;
        }
        return false;
    }
}
