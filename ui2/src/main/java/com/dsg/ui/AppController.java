package com.dsg.ui;

import javax.swing.Icon;
import javax.swing.JPanel;

import com.dsg.nexusmod.ui.Action;
import com.dsg.nexusmod.ui.Controller;
import com.dsg.nexusmod.ui.MenuPlugin;
import com.dsg.nexusmod.ui.OnInit;
import com.dsg.ui.componente.CustomSideMenu;

public class AppController implements MenuPlugin, Controller<JPanelApp> {
	
	
	private JPanelApp panel;
	
	public AppController(JPanelApp panel) {
		this.panel = panel;
	}
	
	private void show(CustomSideMenu.MenuItem itemMenu, Controller<? extends JPanel> controller) {
		
		
		
		if(controller instanceof OnInit) {
			System.out.println("onInit: " + controller.getClass().getName());
			JPanel contentPanel = panel.getContentPanel();
			contentPanel.removeAll();
			((OnInit)controller).onInit(panel.getContext(), contentPanel);
			contentPanel.revalidate();
	    	contentPanel.repaint();
		}
		
	}

	public JPanelApp getPanel() {
		return panel;
	}

	public String addMenuItem(String text, Icon icon, Action action) {
		return getPanel().addMenuItem(text, icon, (item) -> {
				action.executar();
				((CustomSideMenu.MenuItem)item).select(item);
			} );
	}
	
	@Override
	public String addMenuItem(String text, Icon icon, Controller<?> controller) {
		return getPanel().addMenuItem(text, icon, (item) -> show(item, controller) );
	}

	@Override
	public String addMenuItem(String goup, String text, Icon icon, Controller<?> controller) {
		var idMenu = getPanel().addMenuItem(goup, text, icon, (item) -> show(item, controller));
		return idMenu;
	}

	@Override
	public void updateAll(Class<?> classLook) {
		getPanel().updateAll(classLook);
	}
	

}

