package com.dsg.ui;

import javax.swing.Icon;
import javax.swing.JPanel;

import com.dsg.nexusmod.controller.ContextApp;
import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.MenuPlugin;
import com.dsg.nexusmod.controller.OnInit;
import com.dsg.ui.componente.CustomSideMenu;

public class AppController implements MenuPlugin, Controller<JPanelApp> {
	
	private ContextApp context = new ContextAppImp();
	private JPanelApp panel;
	
	public AppController(JPanelApp panel) {
		this.panel = panel;
	}
	
	private void show(CustomSideMenu.MenuItem itemMenu, Controller<? extends JPanel> controller) {
		
		
		context.registerEvent(itemMenu.getId(), (date)-> itemMenu.setBadgeNumber((int)date) );
		
		if(controller instanceof OnInit) {
			((OnInit)controller).onInit(context);
		}
		getPanel().showContent( controller.getPanel() );
	}

	@Override
	public JPanelApp getPanel() {
		return panel;
	}

	@Override
	public void addMenuItem(String text, Icon icon, Controller<?> controller) {
		getPanel().addMenuItem(text, icon, (item) -> show(item, controller) );
		
	}

	@Override
	public void addMenuItem(String goup, String text, Icon icon, Controller<?> controller) {
		getPanel().addMenuItem(goup, text, icon, (item) -> show(item, controller));
		
	}

}

