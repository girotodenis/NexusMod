package com.dsg.nexusmod.plugin;

import org.pf4j.ExtensionPoint;

import com.dsg.nexusmod.ui.MenuPlugin;


public interface ItemMenu extends ExtensionPoint{
	
	void addItemMenu(MenuPlugin menu);

}
