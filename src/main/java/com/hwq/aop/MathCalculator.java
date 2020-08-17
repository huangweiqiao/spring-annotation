package com.hwq.aop;

import org.springframework.stereotype.Component;

@Component("mathCalculator")
public class MathCalculator implements Calculator {

    public String div(int i,int j){
        System.out.println("MathCalculator...div...");
        return "结果="+i/j;
    }
}
