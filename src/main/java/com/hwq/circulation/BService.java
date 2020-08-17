package com.hwq.circulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BService {

    @Autowired
    public AService aService;

    public BService(){
        System.out.println("BService....构造方法....");
    }
}
