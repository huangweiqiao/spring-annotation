package com.hwq.test;

import com.hwq.config.MainConfigOfCirculation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_circulation {

    public static void main(String[] args) {
        String path = IOCTest_circulation.class.getResource("/").getPath();
        System.out.println(path);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfCirculation.class);
        context.close();
    }
}
