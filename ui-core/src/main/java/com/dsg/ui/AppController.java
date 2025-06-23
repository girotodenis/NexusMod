package com.dsg.ui;

import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dsg.nexusmod.controller.AbstractEventListener;
import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.controller.MenuItem;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;
import com.dsg.nexusmod.ui.TaskNotificationType;
import com.dsg.ui.componente.CustomSideMenu;
import com.dsg.ui.componente.NotificacaoEvent;

public class AppController implements ControllerRoot, Controller<JPanelApp> {
	
	
	private JPanelApp panel;
	private JFrame frame;
	private Set<String> oninit = new java.util.HashSet<>();
	
	public AppController(JPanelApp panel,JFrame frame) {
		this.panel = panel;
		this.frame = frame;
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
	
	
	public void showContent(Controller<? extends JPanel> controller) {
		
		if(controller == null) {
			return;
		}
		
		String id = controller.getClass().getName()+controller.hashCode();
		if(!oninit.contains(id)) {
			if(controller instanceof OnChange) {
				((OnInit)controller).onInit(this);
			}
			oninit.add(id);
		}
		
		getPanel().showContent( controller.getPanel() );
		
		if(controller instanceof OnChange) {
			((OnChange)controller).onChage(this);
		}
	}

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
		this.panel.loadMenu();
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

	@Override
	public void addNotification(String message, TaskNotificationType type) {
		ContextApp.getInstance().fireEvent(new NotificacaoEvent(message, type));
	}

	@Override
	public void removeMenu(String itemMenu) {

		getPanel().removeMenu(itemMenu);
	}

	@Override
	public JFrame getFrame() {
		return frame;
	}

}

