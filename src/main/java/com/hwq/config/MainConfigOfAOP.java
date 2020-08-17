package com.hwq.config;

import com.hwq.aop.LogAspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
 * AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】的作用
 * 1、在每一个bean创建之前调用 postProcessBeforeInstantiation()
 *      这里用 MathCalculation和LogAspect的创建举例
 *      1、判断当前bean是否在advisedBeans中(保存了所有需要增强的bean)
 *      2、判断当前bean是否是基础类型（） Advice.class || Pointcut.class || Advisor.class || AopInfrastructureBean.class 或者 @Aspect
 *      3、是否需要跳过
 *          1、获取候选的增强器（切面里的通知方法）【List<Advisor> candidateAdvisors】，每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor
 *              判断增强器是不是AspectJPointcutAdvisor（显然不是）如果是返回true
 *          2、那就是永远返回 false了
 * 2、创建对象
 * postProcessAfterInitialization
 *      return wrapIfNecessary(bean, beanName, cacheKey); //包装如果需要的情况下
 *       1、获取当前bean的所有增强器(通知方法)， Object[] specificInterceptors
 *          1、找到候选的所有增强器(找哪些方法是需要切入当前bean的方法的)
 *          2、找到能在当前bean使用的增强器
 *          3、给增强器排序
 *       2、保存当前bean到advisedBeans中，表示当前bean已经增强处理了
 *       3、如果当前bean需要增强，创建当前bean的代理对象
 *              1、获取所有增强器（通知方法）
 *              2、保存到proxyFactory中
 *              3、创建代理对象，spring自动决定
 *                  JdkDynamicAopProxy;  JDK动态代理
 *                  ObjenesisCglibAopProxy; cglib的动态代理
 *       4、给容器中返回当前组件使用cglib增强了的代理对象
 *       5、以后容器中获取到的就是这个代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程
 *
 * 3、目标方法执行
 *      容器中保存了组件的代理对象（CGLIB增强的对象），这个对象里面保存了详细信息（比如所有的增强器，目标对象，xxx）
 *      1、org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor.intercept() 拦截目标方法的执行
 *      2、根据ProxyFactory对象获取将要执行的目标方法的拦截器链
 *          List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *              1、创建拦截器队列，保存所有拦截器
 *                  List<Object> interceptorList = new ArrayList<Object>(config.getAdvisors().length);
 *                  一个默认的 ExposeInvocationInterceptor 和 4个增强器
 *              2、遍历所有的增强器，将其转为 Interceptor
 *                  registry.getInterceptors(advisor);
 *              3、将增强器转为 List<MethodInterceptor>
 *                  如果是MethodInterceptor则直接加入到集合中
 *                  如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor
 *                  转换完成返回 MethodInterceptor 集合
 *
 *      3、如果没有拦截器链，直接执行目标方法
 *          拦截器链（每一个通知方法又被包装成拦截器，利用 MethodInterceptor 机制）
 *      4、如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等信息传入创建一个 CglibMethodInvocation对象
 *          并调用proceed方法 （ Object retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();）
 *      5、拦截器链的触发过程
 *          1、如果没有拦截器或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）则执行目标方法
 *          2、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成再来执行
 *              通过这种拦截器链的机制，保证通知方法与目标方法的执行顺序
 *
 * 总结
 * 1、@EnableAspectJAutoProxy 开启AOP功能
 * 2、@EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 * 3、AnnotationAwareAspectJAutoProxyCreator 是一个后置处理器
 * 4、容器的创建流程：
 *      1、创建容器时会调用refresh方法，该方法有一个步骤 registerBeanPostProcessors(beanFactory); 注册后置处理器，这里创建 AnnotationAwareAspectJAutoProxyCreator对象
 *      2、finishBeanFactoryInitialization(beanFactory); 初始化剩下的单实例bean
 *          1、创建业务逻辑组件和切面组件
 *          2、AnnotationAwareAspectJAutoProxyCreator 拦截组件的创建过程
 *          3、组件创建完以后，判断组件是否需要增强
 *              是：切面的通知方法包装成增强器(Advisor)；给业务逻辑组件创建一个代理对象（cglib）
 * 5、执行目标方法
 *      1、代理对象执行目标方法
 *      2、CglibAopProxy.intercept()
 *          1、得到目标方法的拦截器链（增强器包装成拦截器 MethodInterceptor ）
 *          2、利用拦截器的链式机制，依次进入每一个拦截器进行执行
 *          3、效果
 *              正常执行：前置通知--》目标方法--》后置通知--》返回通知
 *              发生异常：前置通知--》目标方法--》后置通知--》异常通知
 *
 *
 *
 *
 * ===================================================================
 * 
 *
 *
 *
 */
@ComponentScan("com.hwq.aop")
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {

   /* @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }*/

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
