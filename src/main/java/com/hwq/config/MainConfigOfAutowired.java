package com.hwq.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 自动装配
 *      spring利用依赖注入（DI），完成IOC容器中各个组件的依赖关系赋值
 *
 * 一、spring提供的
 * @Autowired 自动注入，
 *  1、默认优先按照类型取容器中找对应的组件，applicationContext.getBean(BookDao.class)
 *  2、如果找到了多个相同类型的组件，那就再将属性名称作为组件名去容器中查找
 *  3、也可以使用 @Qualifier("bookDao")指定需要的组件，而不是使用属性名查找
 *  4、默认情况下如果配置了 @Autowired 则属性一定要装载成功，但是如果想没有组件也不报错则可以这样配置  @Autowired(required = false)
 *  5、可以使用 @Primary 表示当前生成的组件为首选，以后谁要用这个类型的组件就用这个，就算有多个该类型的组件也不要根据属性名查找了 (当然如果人家在依赖组件的时候使用了 @Qualifier，那还是按Qualifier指定的组件装载 )
 *     @Primary
 *     @Bean
 *     public BookDao bookDao(){
 *         return new BookDao();
 *     }
 *     @Bean("bookDao2")
 *       public BookDao bookDao2(){
 *           return new BookDao();
 *       }
 *
 * 二、java规范提供的
 * JSR250规范提供的 @Resource 和 JSR330规范提供的 @Inject
 *  @Resource //默认是按属性名称装配，但也可以通过 @Resource(name="bookDao2") 指定名字，不过对 @Primary 不支持，也没有@Autowired(required = false)的功能
 *  private BookDao bookDao;
 *
 * @Inject //使用这个需要添加 javax.inject 的maven依赖，和@Autowired功能一样，但没有@Autowired(required = false)的功能
 * private BookDao bookDao;
 *
 *这些自动装配其实都是通过 AutowiredAnnotationBeanPostProcessor 来完成的
 *
 *
 * 三、@Autowired 能放在 属性、构造器、构造器的参数 方法上
   @Autowired
 * public Boss(Car car){
 *    this.car = car;
 * }

   public Boss(@Autowired Car car){  //如果只有一个参数其实这个@Autowired都可以省略
       this.car = car;
   }
 *
 * @Autowired
 * //标注在方法上，srping容器创建当前对象的时候，就会调用方法，完成赋值
 * //方法使用的参数，自定义类型的值从IOC中获取
*  public void setCar(Car car) {
*     this.car = car;
*  }
 *
 *
 *@Bean
 *public Boss boss(@Autowired Car car){ //@Bean标注的方法创建对象的时候，方法参数的值从容器中获取,所以默认可以不用写@Autowired
 *     return new Boss();
 * }
 *
 *
 * 四、自定义组件想要使用spring容器底层的一些组件 （ApplicationContext,BeanFactory,xxx）,
 * 自定义组件实现xxxAware接口,在创建对象的时候，会调用接口规定的方法注入相关组件，可以查看Aware的相关子接口,
 * xxxAware的功能都是使用xxxxProcessor来实现的
 *  ApplicationContextAware====>ApplicationContextProcessor
 *
 * 例如我们的 Red 类的定义
 *
 */
@Configuration
@ComponentScan({"com.hwq.service","com.hwq.dao","com.hwq.controller","com.hwq.bean"})
public class MainConfigOfAutowired {
}
