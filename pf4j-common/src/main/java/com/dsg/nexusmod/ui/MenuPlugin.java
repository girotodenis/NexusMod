package com.dsg.nexusmod.ui;

import javax.swing.Icon;

import com.dsg.nexusmod.controller.Controller;

public interface MenuPlugin {
	
	 void addMenuItem(String text, Icon icon, Controller<?> controller);
	 void addMenuItem(String goup, String text, Icon icon, Controller<?> controller);

}
