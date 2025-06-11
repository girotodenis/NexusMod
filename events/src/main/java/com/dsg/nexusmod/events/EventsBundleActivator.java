package com.dsg.nexusmod.events;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.dsg.nexusmod.log.export.LoggingService;

public class EventsBundleActivator implements BundleActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        ServiceReference<LoggingService> ref = context.getServiceReference(LoggingService.class);
        if (ref != null) {
            LoggingService log = context.getService(ref);
            log.info(EventsBundleActivator.class, "EventsBundleActivator bundle!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    	
    }

}
