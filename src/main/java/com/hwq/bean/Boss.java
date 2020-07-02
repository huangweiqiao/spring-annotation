package com.hwq.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Boss {

    private Car car;

    @Autowired
    public Boss(Car car){
        this.car = car;
    }

}
