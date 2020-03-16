package ru.ifmo.rain.chulkov.hello.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import ru.ifmo.rain.chulkov.hello.api.HelloService;
import ru.ifmo.rain.chulkov.hello.impl.HelloServiceImpl;

public class Activator implements BundleActivator {
    private final String LOG_TAG = "Hello-Service Bundle";

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Start " + LOG_TAG);
        bundleContext.registerService(HelloService.class.getName(),
                new HelloServiceImpl(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Stop " + LOG_TAG);
    }
}