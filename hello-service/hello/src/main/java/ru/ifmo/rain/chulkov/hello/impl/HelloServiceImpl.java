package ru.ifmo.rain.chulkov.hello.impl;

import ru.ifmo.rain.chulkov.hello.api.HelloService;

public class HelloServiceImpl implements HelloService {
    public void sayHello() {
        System.out.println("Hello, world!");
    }
}
