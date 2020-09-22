package com.hwq.config;

import com.hwq.bean2.Dao1;
import com.hwq.bean2.Dao2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * java config 类里只加 @ComponentScan 不加 @Configuration时下面的 dao1() 和 dao2()都是能创建对象的,
 * 但是像下面这种调用，例如 dao2()里调用了 dao1(),这样会创建两个Dao1的对象。
 * 如果加上 @Configuration 就不一样了，这样加了@Bean的方法就不是简单的方法了，调用dao1()时就会去容器中取对象，没有时才创建，
 * 其实加入  @Configuration 后 spring会将这个类产生一个cglib代理类，这样就可以对 里面的方法进行代理，例如调用dao1()方法时，判断是否已经调用过创建了对象
 * 如果创建过就直接从beanFactory里获取。但是如果dao1()方法是static,那么就拦截不到了，因此是static时每次都会创建一个Dao1对象了。
 */
@ComponentScan("com.hwq.bean2")
@Configuration
public class MainConfig3 {

    @Bean
    public Dao1 dao1(){
        Dao1 dao1 = new Dao1();
        System.out.println(dao1);
        return dao1;
    }

    @Bean
    public Dao2 dao2(){
        Dao1 dao1 = dao1();
        return new Dao2();
    }
}
