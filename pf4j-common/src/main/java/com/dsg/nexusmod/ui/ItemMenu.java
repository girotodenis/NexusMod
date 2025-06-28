package com.dsg.nexusmod.ui;

import org.pf4j.ExtensionPoint;

import com.dsg.nexusmod.controller.ControllerRoot;


public interface ItemMenu extends ExtensionPoint{
	
	void addItemMenu(ControllerRoot controllerRoot);

}
