package com.hwq.circulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AService {

    @Autowired
    public BService bService;

    public AService(){
        System.out.println("AService...构造方法...");
    }
}
