package com.dsg.nexusmod.ui;

import org.pf4j.ExtensionPoint;

import com.dsg.nexusmod.controller.MenuPlugin;


public interface ItemMenu extends ExtensionPoint{
	
	void addItemMenu(MenuPlugin menu);

}
