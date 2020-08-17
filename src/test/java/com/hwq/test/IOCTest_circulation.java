package com.hwq.test;

import com.hwq.config.MainConfigOfCirculation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_circulation {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfCirculation.class);
        context.close();
    }
}
