package com.dsg.nexusmod.ui;

import org.pf4j.ExtensionPoint;

import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.osgi.Plugin;


public interface ControllerPlugin extends ExtensionPoint{
	
	Controller newController(ControllerRoot controllerRoot, Plugin plugin);

}
