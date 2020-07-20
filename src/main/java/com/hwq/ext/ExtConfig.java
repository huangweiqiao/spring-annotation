package com.hwq.ext;

import com.hwq.bean.Blue;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扩展原理
 * BeanPostProcessor: bean后置处理器，bean创建对象初始化前后进行拦截工作
 * BeanFactoryPostProcessor: beanFactory的后置处理器
 *      在BeanFactory标准初始化之后调用，所有的bean定义已经保存加载到beanFactory,但是bean的实例还未创建
 *
 * 1、IOC容器创建对象
 *      invokeBeanFactoryPostProcessors(beanFactory); 执行BeanFactoryPostProcessor
 *      如何找到所有BeanFactoryPostProcessor并执行他们的方法，
 *          1、直接在BeanFactory中找BeanFactoryPostProcessor将找出来的对象按是否实现排序接口进行排序然后执行方法； beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false)
 *          2、在初始化创建其他组件之前执行
 *
 * 2、BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *      postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
 *      在所有bean定义信息将要被加载，但是bean实例还未创建的时候
 *
 *      优先于BeanFactoryPostProcessor执行，可以利用BeanDefinitionRegistryPostProcessor给容器中添加额外的组件
 *
 *
 * 原理：
 *  1、IOC容器创建
 *  2、刷新容器 refresh()
 *      invokeBeanFactoryPostProcessors(beanFactory)
 *         beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false); 从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件
 *              依次触发 postProcessBeanDefinitionRegistry方法
 *              依次触发 postProcessBeanFactory方法
 *         beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false); 从容器中获取到所有的BeanFactoryPostProcessor组件
 *              依次触发 postProcessBeanFactory方法
 *
 * ApplicationListener,监听器中发布的事件，事件驱动模型开发
 * public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 *  监听ApplicationEvent及其下面的子事件
 *
 *  步骤：
 *      1、写一个监听器（ApplicationListener实现类）监听某个事件(ApplicationEvent及其子类)
 *      也可以在一个普通方法上加上注解 @EventListener 就达到监听的效果，例如
 *      @EventListener(classes = {ApplicationEvent.class})
 *      public void listen(ApplicationEvent event){
 *         System.out.println("UserService 监听到的事件"+event);
 *      }
 *      @EventListener
 *      原理：使用 public class EventListenerMethodProcessor implements SmartInitializingSingleton, ApplicationContextAware{} 处理器来解析方法的@EventListener注解
 *      看下面关于 SmartInitializingSingleton 的原理
 *
 *
 *      2、把监听器加入容器
 *      3、只要容器中有相关事件的发布，我们就能监听到这个事件
 *          ContextRefreshedEvent 容器刷新完成（所有bean都完成创建）会发布这个事件；
 *          ContextClosedEvent  关闭容器会发布这个事件
 *      4、发布一个事件
 *          applicationContext.publishEvent()
 *
 *
 *   原理：
 *      ContextRefreshedEvent、自定义Event、ContextClosedEvent
 *
 *   ContextRefreshedEvent
 *      1、容器创建，调用刷新方法
 *      2、容器刷新完成 finishRefresh();
 *      【事件发布流程】
 *      3、publishEvent(new ContextRefreshedEvent(this)); 不只是容器的刷新，自己发布的事件也会走这个流程
 *          获取事件的多播器，派发事件 getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
 *              获取到所有的ApplicationListener for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
 *                  1、如果有Executor,可以支持使用Executor进行异步派发
 *                  Executor executor = getTaskExecutor();
 *
 *                  2、否则同步的方式执行listener的方法，invokeListener(listener,event)
 *                  拿到listener回调onApplicationEvent方法
 *              }
 *
 *  【事件的多播器是怎么拿到的呢】
 *      1、容器创建，调用刷新方法
 *      2、initApplicationEventMulticaster(); 初始化多播器
 *          先判断容器中是否有id为 applicationEventMulticaster 的组件，
 *          如果有则从容器中取否则创建 SimpleApplicationEventMulticaster 并注册到容器中，我们就可以在其他组件要派发事件，自动注入这个applicationEventMulticaster
 *
 *   【容器中有哪些监听器】
 *      1、容器创建，调用刷新方法
 *      2、registerListeners();
 *          从容器中拿到所有的监听器
 *          String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *          依次加入到多播器中 getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *
 *
 *    SmartInitializingSingleton原理：--->afterSingletonsInstantiated()
 *      1、IOC容器创建对象并刷新容器
 *      2、finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean
 *          1、先创建所有的单实例bean,调用getBean()
 *          2、获取所有创建好的单实例bean 判断是否是 SmartInitializingSingleton类型的
 *              如果是就调用afterSingletonsInstantiated()
 *
 *
 *
 */
@ComponentScan("com.hwq.ext")
@Configuration
public class ExtConfig {
//    BeanFactoryPostProcessor

    @Bean
    public Blue blue(){
        return new Blue();
    }
}
