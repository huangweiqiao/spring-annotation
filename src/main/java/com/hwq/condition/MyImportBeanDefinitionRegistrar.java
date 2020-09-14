package com.hwq.condition;

import com.hwq.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param importingClassMetadata 当前类的注解信息
     * @param registry 注册组件到容器中的类
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean red = registry.containsBeanDefinition("com.hwq.bean.Red");
        boolean blue = registry.containsBeanDefinition("com.hwq.bean.Blue");
        if (red && blue){
            //如果容器中包含了 red 和 blue 则我们向容器中注入rainBow组件
            //指定bean定义
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            //注册一个bean，指定bean名
            registry.registerBeanDefinition("rainBow",rootBeanDefinition);
        }

        /*
        (可以动态的注册bean,例如项目里有A接口没有实现类，
 *      那么我们可以定义一个类实现ImportBeanDefinitionRegistrar，然后在实现的方法中 创建A接口的动态代理对象，然后注册到容器中，
 *      创建A接口的动态代理对象可以通过实现 FactoryBean接口，在getObject方法中创建A的动态代理对象。)
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue("com.hwq.bean.A"); // AFactoryBean 这个类中有构造函数，构造函数有一个类型是 com.hwq.bean.A 的参数
        beanDefinition.setBeanClass(AFactoryBean.class); //在AFactoryBean类中创建动态代理类
        registry.registerBeanDefinition("a",beanDefinition);*/
    }
}
