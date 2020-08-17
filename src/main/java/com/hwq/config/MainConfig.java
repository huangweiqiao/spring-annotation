package com.hwq.config;

import com.hwq.bean.Person;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

//配置类==配置文件，告诉spring这是一个配置类
@Configurable
@ComponentScan(value = "com.hwq.bean"

        /*,excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes ={Controller.class, Service.class} )
        }*/

         /*, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ANNOTATION,classes ={Controller.class}) ,//按注解包含
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {BookService.class}), //按指定的类型包含
            @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class}) //自定义的包含规则
        }
        ,useDefaultFilters=false*/
)
/*
ComponentScan指定自动扫描的包,这样这个包下所有@Controller,@Service,@Repository,@Component 修饰的类 就能自动注册到IOC容器中了
相当于xml文件中的 <context:component-scan base-package=""></context>
value 指定要扫描的包，
excludeFilters 表示要将那些排除掉
includeFilters表示只包含哪些，使用includeFilters要禁用掉默认规则，因此@ComponentScan要加上useDefaultFilters=false
相当于<context:component-scan base-package="" use-default-filter="false"></context>

@ComponentScan 这个组件是可以写多次的，例如：
@ComponentScan(value = "com.hwq.abc")
@ComponentScan(value = "com.hwq.def")
也可以用@ComponentScans 定义多个扫描规则
@ComponentScans(
        value = {
                @ComponentScan(value="com.hwq.abc"),
                @ComponentScan(value="com.hwq.abc")
        }
)
*/
public class MainConfig {
    //给容器中注册一个bean,类似于xml文件中<bean>,类型为返回值的类型，id默认是用方法名作为id
    @Bean
    public Person person(){
        return new Person("hwq",30);
    }
}
