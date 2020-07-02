package com.hwq.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {
    //使用@Value复制
    //1、基本数值   @Value("张三")   @Value(20)
    //2、spel表达式  @Value("#{20-2}")
    //3、可以写${}  取出配置文件properties文件种的值(在运行环境变量里面的值)  @Value("${环境变量名}")
    @Value("张三")
    private String name;

    @Value("#{20-2}")
    private int age;

    @Value("${person.nickName}")
    private String nickName;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, String nickName) {
        this.name = name;
        this.age = age;
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName=" + nickName +
                '}';
    }
}
