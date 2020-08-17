package com.hwq.aop;

import org.springframework.stereotype.Component;

@Component("myPrint")
public class MyPrint implements Print{
    @Override
    public void print() {
        System.out.println("MyPrint....print...");
    }
}
