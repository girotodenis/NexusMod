package com.dsg.nexusmod.plugin;

import org.pf4j.ExtensionPoint;

public interface MessageListener extends ExtensionPoint{
	
	void onMessage(String senderPluginId, String message);

}
