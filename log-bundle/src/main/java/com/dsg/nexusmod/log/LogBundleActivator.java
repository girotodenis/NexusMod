package com.dsg.nexusmod.log;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import com.dsg.nexusmod.plugin.MessageListener;

public class LogBundleActivator extends Plugin {

    public LogBundleActivator(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class MessageListenerImpl implements MessageListener {

    	LoggingServiceImpl loggingService = new LoggingServiceImpl();
    	
    	@Override
    	public void onMessage(String senderPluginId, String message) {
    		loggingService.info(LogBundleActivator.class, "Received message from plugin {}: {}", senderPluginId, message);
    	}

    }

}



