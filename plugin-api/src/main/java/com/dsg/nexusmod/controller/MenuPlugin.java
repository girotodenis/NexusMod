package com.dsg.nexusmod.controller;

import javax.swing.Icon;

public interface MenuPlugin {
	
	 void addMenuItem(String text, Icon icon, Controller<?> controller);
	 void addMenuItem(String goup, String text, Icon icon, Controller<?> controller);

}
