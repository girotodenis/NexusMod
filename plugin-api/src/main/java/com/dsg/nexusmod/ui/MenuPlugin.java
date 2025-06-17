package com.dsg.nexusmod.ui;

import javax.swing.Icon;

public interface MenuPlugin {
	
	 String addMenuItem(String text, Icon icon, Controller<?> controller);
	 String addMenuItem(String goup, String text, Icon icon, Controller<?> controller);

}
