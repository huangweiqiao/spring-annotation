package com.hwq.proxy;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        TargetImpl target = new TargetImpl();
        TargetInterface proxy = (TargetInterface)Proxy.newProxyInstance(target.getClass().getClassLoader(),
                new Class[]{TargetInterface.class},
                new MyInvocationHandler(target));
        proxy.print();
    }
}
