package com.dsg.ui;

import javax.swing.JPanel;

import com.dsg.nexusmod.controller.AbstractEventListener;
import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.controller.MenuItem;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;
import com.dsg.ui.componente.CustomSideMenu;

public class AppController implements ControllerRoot, Controller<JPanelApp> {
	
	
	private JPanelApp panel;
	
	public AppController(JPanelApp panel) {
		this.panel = panel;
	}
	
	private void show(CustomSideMenu.MenuItem itemMenu, Controller<? extends JPanel> controller) {
		
		if(controller == null) {
			return;
		}
		
		getPanel().showContent( controller.getPanel() );
		
		if(controller instanceof OnChange) {
			((OnChange)controller).onChage(this);
		}
	}
	
	
//	private void showContent(Controller<? extends JPanel> controller) {
//		
//		if(controller == null) {
//			return;
//		}
//		
//		if(controller instanceof OnChange) {
//			((OnInit)controller).onInit(this);
//		}
//		
//		if(controller instanceof OnChange) {
//			((OnChange)controller).onChage(this);
//		}
//		
//		getPanel().showContent( controller.getPanel() );
//	}

	@Override
	public JPanelApp getPanel() {
		return panel;
	}

	@Override
	public void addMenuItem(MenuItem menuItem) {
		getPanel().addMenuItem(menuItem.getGroup(), menuItem.getText(), menuItem.getIcon(), (item) -> show(item, menuItem.getController()));
		
		if(menuItem.getController() instanceof OnInit) {
			((OnInit)menuItem.getController()).onInit(this);
		}
	}

	@Override
	public <T> void fireEvent(T event) {
		ContextApp.getInstance().fireEvent(event.getClass().getName(), event);;
		
	}

	@Override
	public <T> void fireEvent(String event, T date) {
		ContextApp.getInstance().fireEvent(event, date);
	}

	@Override
	public <T> void menuEvent(String menu, String event, T date) {
		ContextApp.getInstance().fireEvent(String.format("com.dsg.ui.componente.CustomSideMenu$MenuItem.%s.%s", menu, event), date);
	}

	@Override
	public <T> void registerEvent(Class<T> eventClass, AbstractEventListener<T> eventListener) {
		ContextApp.getInstance().registerEvent(eventClass.getName(), eventListener);;
		
	}

	@Override
	public <T> void registerEvent(String event, AbstractEventListener<T> eventListener) {
		ContextApp.getInstance().registerEvent(event, eventListener);
	}

}

