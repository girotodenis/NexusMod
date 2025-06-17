package com.dsg.ui;

import javax.swing.Icon;
import javax.swing.JPanel;

import com.dsg.nexusmod.ui.ContextApp;
import com.dsg.nexusmod.ui.Controller;
import com.dsg.nexusmod.ui.MenuPlugin;
import com.dsg.nexusmod.ui.OnInit;
import com.dsg.ui.componente.CustomSideMenu;

public class AppController implements MenuPlugin, Controller<JPanelApp> {
	
	private ContextApp context = new ContextAppImp();
	private JPanelApp panel;
	
	public AppController(JPanelApp panel) {
		this.panel = panel;
	}
	
	private void show(CustomSideMenu.MenuItem itemMenu, Controller<? extends JPanel> controller) {
		
		
		context.registerEvent(itemMenu.getId()+".badgeNumber", (date)-> itemMenu.setBadgeNumber((int)date) );
		context.registerEvent(itemMenu.getId()+".visible", (date)-> itemMenu.setVisible((boolean)date) );
		
		if(controller instanceof OnInit) {
			System.out.println("onInit: " + controller.getClass().getName());
			((OnInit)controller).onInit(context);
		}
		getPanel().showContent( controller.getPanel() );
		System.out.println("fim show: " + controller.getClass().getName());
	}

	@Override
	public JPanelApp getPanel() {
		return panel;
	}

	@Override
	public String addMenuItem(String text, Icon icon, Controller<?> controller) {
		return getPanel().addMenuItem(text, icon, (item) -> show(item, controller) );
	}

	@Override
	public String addMenuItem(String goup, String text, Icon icon, Controller<?> controller) {
		return getPanel().addMenuItem(goup, text, icon, (item) -> show(item, controller));
	}

}

