package com.hwq.config;

import com.hwq.bean.Color;
import com.hwq.bean.ColorFactoryBean;
import com.hwq.bean.Person;
import com.hwq.condition.LinuxCondition;
import com.hwq.condition.MyImportBeanDefinitionRegistrar;
import com.hwq.condition.MyImportSelector;
import com.hwq.condition.WidowsCondition;
import org.springframework.context.annotation.*;

//@Conditional(LinuxCondition.class) //Conditional也是可以放在类上的，如果放在类上，则满足条件类才能生效
/**
 * 给容器中注册组件的方式
 *  1、包扫描+组件标注注解 （@Controller/@Service/@Repository/@Component）
 *  2、@Bean (导入第三方包里的组件)
 *  3、@Import (快速给容器中导入组件)
 *      1、@Import (要导入的类)；容器中就会自动注册这个组件，id默认为全类名；
 *      2、ImportSelector: 返回需要导入的组件的全类名数组；
 *      3、ImportBeanDefinitionRegistrar
 *  4、使用srping提供的 FactoryBean(工厂bean,区别于普通bean这是一个工厂，用来生成其他bean对象)
 */
@Configuration
@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {
    /**
     *Scope 默认是 singleton(单实例)  @Scope("singleton") ioc容器启动会调用方法创建对象放到IOC容器中，
     *以后要使用的时候就从容器中拿。
     *也可以设置成 prototype(多实例)  @Scope("prototype") ioc容器启动并不会去创建对象，而是每次获取的时候
     * 才去创建对象。
     *
     * 懒加载
     *  针对单实例 可以设置 @Lazy 懒加载，等第一次获取的时候才创建对象
     */
    @Scope
    @Lazy
    @Bean(name="person")
    public Person person(){
        System.out.println("给容器中添加person...");
        return new Person("hhc",5);
    }

    /**
     * @Conditional 按照一定条件进行判断，满足条件给容器中注册bean
     * 例如：如果系统是widonw则给容器中注册 bill,如果系统是linux则给容器中注册linus
     * @Conditional 也是可以放在类上的，如果放在类上，这满足条件类才能生效
     *
     * @return
     */
    @Conditional({WidowsCondition.class})
    @Bean("bill")
    public Person person01(){
        System.out.println("给容器中添加bill...");
        return new Person("Bill Gates",62);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person2(){
        System.out.println("给容器中添加linus...");
        return new Person("linus",48);
    }

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }

}
