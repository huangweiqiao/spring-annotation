package com.hwq.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 判断是否widowns系统
 */
public class WidowsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取当前环境信息
        Environment environment = conditionContext.getEnvironment();
        //获取bean定义的注册信息
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        //是否liunx系统
        String osname =environment.getProperty("os.name");
        if (osname.contains("Windows")){
            return true;
        }
        return false;
    }
}
