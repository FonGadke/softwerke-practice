package ru.ifmo.rain.chulkov.hellouser;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import ru.ifmo.rain.chulkov.hello.api.HelloService;

public class HelloClient implements BundleActivator {
    private final String LOG_TAG = "Hello-Service-User Bundle";

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Start " + LOG_TAG);
        ServiceReference helloServiceRef = bundleContext.getServiceReference(HelloService.class.getName());
        if (helloServiceRef != null) {
            HelloService service = ((HelloService) bundleContext.getService(helloServiceRef));
            if (service != null) {
                service.sayHello();
            } else {
                System.err.println("Can't get service object of " + HelloService.class.getName());
            }
        } else {
            System.err.println("Can't get ServiceReference object for a service named " + HelloService.class.getName());
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Stop " + LOG_TAG);
    }
}
