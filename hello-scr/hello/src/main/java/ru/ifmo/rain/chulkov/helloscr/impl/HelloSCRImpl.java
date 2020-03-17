package ru.ifmo.rain.chulkov.helloscr.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import ru.ifmo.rain.chulkov.helloscr.api.HelloSCR;

@SuppressWarnings("deprecation")
@Component
@Service(value = HelloSCR.class)
public class HelloSCRImpl implements HelloSCR {

    @Override
    public void sayHello() {
        System.out.println("Hello OSGi World!");
    }

    @Activate
    void onActive() {
        System.out.println("Start Hello-Service-Component-Runtime Bundle");
    }

    @Deactivate
    void close() {
        System.out.println("Stop Hello-Service-Component-Runtime Bundle");
    }
}