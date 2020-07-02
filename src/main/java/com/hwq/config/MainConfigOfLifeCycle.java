package com.hwq.config;

import com.hwq.bean.Car;
import com.hwq.bean.Cat;
import com.hwq.bean.InitBeanOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * bean的生命周期
 *  bean创建，初始化，销毁的过程
 *  容器管理bean的生命周期
 *  我们可以自定义初始化和销毁方法，容器的bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁
 *
 *  构造（对象创建）
 *      单实例，在容器启动的时候创建对象
 *      多实例，在每次获取的时候创建对象
 *
 *
 *  初始化
 *      对象创建完成，并赋值好，调用初始化方法
 *
 *
 * populateBean(beanName, mbd, instanceWrapper); //给bean对象属性赋值 getter setter方法
 * initializeBean(beanName, exposedObject, mbd){
 *  //遍历得到容器中所有BeanPostProcessor,挨个执行 postProcessBeforeInitialization方法，一旦返回null,直接跳出for循环，后面的postProcessBeforeInitialization方法都不会执行
 *  applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *  //初始化， 例如指定的 init_method, @PostConstruct, 实现的InitializingBean接口的方法
 *  invokeInitMethods(beanName, wrappedBean, mbd);
 *  applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 * }
 *  销毁
 *      单实例bean是在容器关闭的时候调用销毁的方法，多实例bean容器是不会调用销毁方法
 *
 *  1、指定初始化和销毁方法
 *      @Bean(initMethod = "init",destroyMethod = "destory")
 *      类似于基于xml的方法是 <bean init="xxx" destory-method=""></bean>,这些方法不能有参数，可以抛异常
 *
 *  2、通过让bean实现InitializingBean(定义初始化逻辑)， DisposableBean(定义销毁逻辑)
 *
 *  3、可以使用JSR250规范
 *      @PostConstruct  在bean创建完成并且属性赋值完成，来执行初始化
 *      @PreDestroy  在容器销毁bean之前通知我们执行清理工作
 *
 *  4、通过实现BeanPostProcessor (bean的后置处理器)
 *      在bean初始化前后进行一些处理工作
 *      postProcessBeforeInitialization  任何初始化前调用该方法
 *      postProcessAfterInitialization   任何初始化后调用该方法
 *
 *
 *  初始化顺序
 *      1、initBeanOrder constructor (构造方法)
 *      2、initBeanOrder postConstruct (@PostConstruct 注解的方法)
 *      3、initBeanOrder afterPropertiesSet (实现InitializingBean接口的方法)
 *      4、initBeanOrder initMethod  (@Bean 中指定的初始方法)
 *      5、initBeanOrder postProcessBeforeInitialization (实现BeanPostProcessor接口的方法)
 *      6、initBeanOrder postProcessAfterInitialization  (实现BeanPostProcessor接口的方法)
 *      7、initBeanOrder preDestroy (@PreDestroy注解的方法)
 *      8、initBeanOrder destroy  (实现DisposableBean接口的方法)
 *      9、initBeanOrder destoryMethod (@Bean 中指定的销毁方法)
 *
 *
 */
//@ComponentScan("com.hwq.bean")
@Configuration
public class MainConfigOfLifeCycle {

//    @Bean(initMethod = "init",destroyMethod = "destory")
//    public Car car(){
//        return new Car();
//    }
    @Primary
    @Bean(initMethod = "initMethod",destroyMethod = "destoryMethod")
    public InitBeanOrder initBeanOrder(){
        return new InitBeanOrder();
    }

}
