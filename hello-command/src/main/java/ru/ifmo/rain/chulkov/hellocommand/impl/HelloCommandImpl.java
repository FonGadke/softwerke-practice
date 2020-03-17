package ru.ifmo.rain.chulkov.hellocommand.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import ru.ifmo.rain.chulkov.hellocommand.api.HelloCommand;

@SuppressWarnings("deprecation")
@Component
@Service(value = Object.class)
@Properties({
        @Property(name = "osgi.command.scope", value = "practice"),
        @Property(name = "osgi.command.function", value = "hello")
})

public class HelloCommandImpl implements HelloCommand {

    @Override
    public void hello(String param) {
        System.out.println("Hello, " + param);
    }
}
