package com.hwq.ext;

import com.hwq.bean.Blue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

/**
 * BeanDefinitionRegistryPostProcessor 是 BeanFactorydPostProcessor的子类，但是在BeanFactorydPostProcessor之前被执行，
 * 因为spring源码中包扫描时先遍历 BeanDefinitionRegistryPostProcessor(有spring提供的，也有自定义的（手动调用context.addBeanFactoryPostProcessor()才算自定义，自定义的会被先执行）)
 *
 * BeanDefinitionRegistryPostProcessor 能够让我们编写代码根据实际情况动态注册bean
 *
 * 经典应用：
 * ConfigurationClassPostProcessor 中有使用的
 *
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor...postProcessBeanFactory...bean的数量:"+beanFactory.getBeanDefinitionCount());
    }

    //BeanDefinitionRegistry Bean定义信息的保存中心，以后BeanFacotry就是按照BeanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor...postProcessBeanDefinitionRegistry....bean的数量:"+registry.getBeanDefinitionCount());
//        RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition();
        registry.registerBeanDefinition("hello",beanDefinition);
    }
}
