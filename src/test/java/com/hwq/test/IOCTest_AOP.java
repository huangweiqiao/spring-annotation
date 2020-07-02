package com.hwq.test;

import com.hwq.aop.MathCalculator;
import com.hwq.config.MainConfigOfAOP;
import com.hwq.config.MainConfigOfAutowired;
import com.hwq.dao.BookDao;
import com.hwq.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_AOP {
    @Test
    public void test01(){

        AnnotationConfigApplicationContext configApplicationContext =
                new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        MathCalculator calculator = configApplicationContext.getBean(MathCalculator.class);
        calculator.div(10,2);
        configApplicationContext.close();
    }

}
