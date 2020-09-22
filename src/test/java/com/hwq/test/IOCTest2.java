package com.hwq.test;

import com.hwq.config.MainConfig3;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class IOCTest2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig3.class);

    }
}
