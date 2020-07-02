package com.hwq.test;

import com.hwq.bean.Person;
import com.hwq.config.MainConfigOfAutowired;
import com.hwq.config.MainConfigOfPropertyValues;
import com.hwq.dao.BookDao;
import com.hwq.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

public class IOCTest_Autowired {
    private AnnotationConfigApplicationContext configApplicationContext =
            new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);

    @Test
    public void test01(){

        BookService bookService = configApplicationContext.getBean(BookService.class);
        System.out.println(bookService);

        BookDao bookDao = configApplicationContext.getBean(BookDao.class);
        System.out.println(bookDao);

        configApplicationContext.close();
    }

}
