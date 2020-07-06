package com.hwq.config;

import com.hwq.aop.LogAspects;
import com.hwq.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP
 * 1、导入aop模块，spring AOP  (spring-aspects)
 * 2、定义业务逻辑类 （MathCalculator），在业务逻辑运行的时候将日志进行打印 （方法前，方法后，出现异常时，等。。。）
 * 3、定义一个切面类（LogAspects）,切面类里面的方法需要动态感知 MathCalculator.div运行状况
 *      通知方法：
 *          前置通知(@Before)：logStart ,在目标方法（div）运行之前运行
 *          后置通知(@After)：logEnd ,在目标方法(div) 运行结束之后运行,不管方法正常结束还是异常结束
 *          返回通知(@AfterReturning)：logReturn , 在目标方法(div) 正常返回之后运行
 *          异常通知(@AfterThrowing)：logException , 在目标方法(div) 出现异常以后运行
 *          环绕通知(@Around)：动态代理，手动推进目标方法运行 （joinPoint.procced()）
 * 4、给切面类的目标方法标注何时何地运行（通知注解）
 * 5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 * 6、必须告诉spring那个类是切面类，在切面类上加一个注解@Aspect
 * 7、在主配置类上加注解@EnableAspectJAutoProxy开启基于注解版的切面功能，类似于配置文件中<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
 *      在spring中有很多的@Enablexxx,表示开启什么功能
 *
 * 概括起来就三步：
 * 1、将业务逻辑组件和切面类都加入到容器中，并告诉spring哪个是切面类(@Aspect)
 * 2、切面类的每个通知方法上标注通知注解，告诉spring何时何地运行(切入点表达式)
 * 3、开启基于注解的AOP模式， @EnableAspectJAutoProxy
 *
 * AOP原理：【给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么】
 *  @EnableAspectJAutoProxy
 *  1、@EnableAspectJAutoProxy是什么？
 *      @Import(AspectJAutoProxyRegistrar.class)，给容器中导入了AspectJAutoProxyRegistrar组件
 *      利用AspectJAutoProxyRegistrar自定义给容器中注册bean
 *      给容器中注册一个名叫org.springframework.aop.config.internalAutoProxyCreator的 AnnotationAwareAspectJAutoProxyCreator 的RootBeanDefinition对象
 *  2、AnnotationAwareAspectJAutoProxyCreator
 *      ->AspectJAwareAdvisorAutoProxyCreator
 *          ->AbstractAdvisorAutoProxyCreator
 *             ->AbstractAutoProxyCreator
 *                  ->implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                  关注这个后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
 *
 * AbstractAutoProxyCreator有setBeanFactory方法
 * AbstractAutoProxyCreator有后置处理器逻辑
 *
 * AbstractAdvisorAutoProxyCreator又重写了setBeanFactory方法 调用了父类的setBeanFactory后还调用了initBeanFactory方法
 *
 * AspectJAwareAdvisorAutoProxyCreator
 *
 * AnnotationAwareAspectJAutoProxyCreator有initBeanFactory方法，其实就是重写了父类AbstractAdvisorAutoProxyCreator的initBeanFactory方法
 *
 *流程：
 * 1、传入主配置类创建IOC容器
 * 2、注册配置类，调用refresh方法刷新容器
 * 3、registerBeanPostProcessors(beanFactory) 注册bean的后置处理器来方便拦截bean的创建
 *      1、先获取IOC容器中已经定义了的需要创建对象的所有BeanPostProcessor
 *      2、给容器中加上其他的BeanPostProcessor
 *      3、优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *      4、再给容器中注册实现了Ordered接口的BeanPostProcessor
 *      5、最后注册没实现优先级接口的BeanPostProcessor
 *      6、注册BeanPostProcessor,实际上就是创建BeanPostProcessor对象，保存在容器中
 *          创建 internalAutoProxyCreator的BeanPostProcessor[AnnotationAwareAspectJAutoProxyCreator]
 *              1、createBeanInstance方法创建实例
 *              2、populateBean方法给bean的各种属性赋值
 *              3、initializeBean初始化bean
 *                  1、invokeAwareMethods() 这个Aware方法接口的回调
 *                  2、applyBeanPostProcessorsBeforeInitialization(),应用后置处理器 postProcessBeforeInitialization方法就是在这里调用
 *                  3、invokeInitMethods() 执行初始化方法
 *                  4、applyBeanPostProcessorsAfterInitialization ，应用后置处理器 postProcessAfterInitialization 方法就是在这里调用的
 *              4、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功，并且创建了aspectJAdvisorsBuilder对象
 *      7、把BeanPostProcessor注册BeanFactory中
 *          beanFactory.addBeanPostProcessor()
 *
 *===============================以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程===============================
 *
 *     AnnotationAwareAspectJAutoProxyCreator 是一个 InstantiationAwareBeanPostProcessor
 * 4、// Instantiate all remaining (non-lazy-init) singletons.
 * 	  finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作，创建剩下的单实例
 * 	  1、遍历获取容器中所有的Bean,依次创建对象getBean(beanName);
 * 	    getBean-->doGetBean()-->getSingleton()-->创建bean
 * 	  2、创建bean
 * 	    【AnnotationAwareAspectJAutoProxyCreator在所有Bean创建之前会有一个拦截，因为它是InstantiationAwareBeanPostProcessor，会调用postProcessBeforeInstantiation】
 * 	    1、先从缓存中获取当前bean,如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建
 * 	        只要创建好的bean会被缓存起来
 * 	    2、创建bean createBean() ，AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前先尝试返回bean的实例
 * 	        【BeanPostProcessor是Bean对象创建完成初始化前后调用的】
 * 	        【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象，AnnotationAwareAspectJAutoProxyCreator属于该类型】
 * 	        1、resolveBeforeInstantiation（）解析BeforeInstantiation，希望后置处理器在此能返回一个代理对象，如果能返回代理对象就使用，如果不能就继续。
 * 	                拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor这个类型就执行后置处理器的 postProcessBeforeInstantiation 方法
 * 	                bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 * 					if (bean != null) {
 * 						bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                  }
 * 	        2、doCreateBean(beanName, mbdToUse, args);真正的去创建一个bean实例，和3.6流程一样
 *
 *
 *
 *
 *
 */

@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {

    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
