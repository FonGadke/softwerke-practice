package ru.ifmo.rain.chulkov.hellosrcuser;

import org.apache.felix.scr.annotations.*;
import ru.ifmo.rain.chulkov.helloscr.api.HelloSCR;

@SuppressWarnings("deprecation")
@Component
public class HelloSCRClient {

    @Reference(policy = ReferencePolicy.DYNAMIC)
    private volatile HelloSCR hello;

    @Activate
    void onActive() {
        hello.sayHello();
    }

    @Deactivate
    void close() {
        System.out.println("Stop Hello-Service-Component-Runtime-Client Bundle");
    }
}
