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


        System.out.println("=====================================");

        Calculator calculator2 = (Calculator)configApplicationContext.getBean("myCalculator");
        calculator2.div(10,5);


        System.out.println("*****************************************");
        Calculator calculator3 = (Calculator)configApplicationContext.getBean("myPrint");
        calculator2.div(10,5);

        configApplicationContext.close();

    }

}
