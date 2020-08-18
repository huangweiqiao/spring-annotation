package com.hwq.aop;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("mathCalculator")
@Scope("prototype")
public class MathCalculator implements Calculator {

    public String div(int i,int j){
        System.out.println("MathCalculator...div...");
        return "结果="+i/j;
    }
}
