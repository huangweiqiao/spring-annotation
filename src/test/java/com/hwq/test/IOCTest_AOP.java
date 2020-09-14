package com.hwq.test;

import com.hwq.aop.Calculator;
import com.hwq.config.MainConfigOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_AOP {
    @Test
    public void test01(){

        AnnotationConfigApplicationContext configApplicationContext =
                new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        Calculator calculator = (Calculator)configApplicationContext.getBean("mathCalculator");
        calculator.div(10,5);
        calculator.div(9,3);

        System.out.println("--------------------------------------");

        Calculator calculator1 = (Calculator)configApplicationContext.getBean("mathCalculator");
        calculator1.div(9,3);


        System.out.println("=====================================");

        Calculator calculator2 = (Calculator)configApplicationContext.getBean("myCalculator");
        calculator2.div(10,5);


        System.out.println("*****************************************");
        //MyPrint其实没有显示的实现Calculator接口，根本没有div方法，但是在 aspect 类里加了下面这两行，于是spring自动帮忙实现了Calculator接口，并且默认实现方式跟MathCalculator一样
        //@DeclareParents(value="com.hwq.aop.*+", defaultImpl= MathCalculator.class)
        //public static Calculator mixin;
        Calculator calculator3 = (Calculator)configApplicationContext.getBean("myPrint");
        calculator2.div(10,5);

        configApplicationContext.close();

    }

}
