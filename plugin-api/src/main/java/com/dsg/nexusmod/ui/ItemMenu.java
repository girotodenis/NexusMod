package com.dsg.nexusmod.ui;

import org.pf4j.ExtensionPoint;

import br.com.dsg.legui.controller.ActionMenu;
import br.com.dsg.legui.controller.LeGuiController;


public interface ItemMenu extends ExtensionPoint{
	
	String nome(); 
	String imageA();
	String imageB();
	boolean imageHorizontalAlignRIGHT();
	boolean desabilitarSelecaoMenu();
	ActionMenu<LeGuiController> action();
	boolean configSubMenuButtonMouse2();

}
