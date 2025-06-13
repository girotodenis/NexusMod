package com.dsg.nexusmod.ui;

import org.pf4j.ExtensionPoint;

import br.com.dsg.legui.controller.StartLeGui;


public interface ItemMenu extends ExtensionPoint{
	
	void addItemMenu(StartLeGui app);

}
