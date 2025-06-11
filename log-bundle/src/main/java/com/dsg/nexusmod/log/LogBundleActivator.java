package com.dsg.nexusmod.log;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dsg.nexusmod.log.export.LoggingService;

public class LogBundleActivator implements BundleActivator {
    
	LoggingServiceImpl service = new LoggingServiceImpl();
	
	@Override
    public void start(BundleContext context) throws Exception {
        context.registerService(LoggingService.class, service, null);
        service.info(LogBundleActivator.class, "LogBundleActivator bundle started!");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    	service.info(LogBundleActivator.class, "LogBundleActivator bundle stopped!");
    }
}

