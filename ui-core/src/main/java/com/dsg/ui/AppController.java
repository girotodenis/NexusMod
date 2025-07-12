package com.dsg.ui;

import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsg.nexusmod.controller.AbstractEventListener;
import com.dsg.nexusmod.controller.Controller;
import com.dsg.nexusmod.controller.ControllerContent;
import com.dsg.nexusmod.controller.ControllerRoot;
import com.dsg.nexusmod.controller.MenuItem;
import com.dsg.nexusmod.model.Progress;
import com.dsg.nexusmod.ui.OnChange;
import com.dsg.nexusmod.ui.OnInit;
import com.dsg.nexusmod.ui.TaskNotificationType;
import com.dsg.ui.componente.CustomSideMenu;
import com.dsg.ui.componente.NotificacaoEvent;
import com.dsg.ui.componente.TaskNotificationManager;

public class AppController implements ControllerRoot, ControllerContent<JPanelApp> {
	
	private static final Logger log = LoggerFactory.getLogger(AppController.class);
	
	private JPanelApp panel;
	private JFrame frame;
	private Set<String> oninit = new java.util.HashSet<>();
	private TaskNotificationManager notificationManager;
	
	private final AbstractEventListener<Progress> eventProgressBar = (event) -> {
		panel.updateProgress(event.getValue(), event.getMessage());
	};
	
	private final AbstractEventListener<NotificacaoEvent> eventTask = (data)->{
		notificationManager.addNotification(data.getMensagem(), data.getTipo());
	};
	
	public AppController(JPanelApp panel,JFrame frame) {
		this.panel = panel;
		this.frame = frame;
		this.notificationManager = new TaskNotificationManager(frame.getLayeredPane());
		
		registerEvent(Progress.class, eventProgressBar);
		registerEvent(NotificacaoEvent.class, eventTask);
	}
	
	private void show(CustomSideMenu.MenuItem itemMenu, ControllerContent<? extends JPanel> controller) {
		
		if(controller == null) {
			return;
		}
		
		String id = controller.getClass().getName()+controller.hashCode();
		if(!oninit.contains(id)) {
			if(controller instanceof OnInit) {
				log.trace("onInit: {}",controller.getClass().getSimpleName());
				((OnInit)controller).onInit(this);
			}
			oninit.add(id);
		}
		
		if(controller instanceof OnChange) {
			log.trace("onChange: {}",controller.getClass().getSimpleName());
			((OnChange)controller).onChage(this);
		}
		
		getPanel().showContent( controller.getPanel() );
	}
	
	
	public void header(ControllerContent<? extends JPanel> controller) {
		if(controller == null) {
			return;
		}
		String id = controller.getClass().getName()+controller.hashCode();
		if(!oninit.contains(id)) {
			if(controller instanceof OnInit) {
				log.trace("onInit: {}",controller.getClass().getSimpleName());
				((OnInit)controller).onInit(this);
			}
			oninit.add(id);
		}
		
		if(controller instanceof OnChange) {
			log.trace("onChange: {}",controller.getClass().getSimpleName());
			((OnChange)controller).onChage(this);
		}
		getPanel().showHeader(controller.getPanel());
	}
	

	public void showContent(ControllerContent<? extends JPanel> controller) {
		
		if(controller == null) {
			return;
		}
		
		String id = controller.getClass().getName()+controller.hashCode();
		if(!oninit.contains(id)) {
			if(controller instanceof OnInit) {
				log.trace("onInit: {}",controller.getClass().getSimpleName());
				((OnInit)controller).onInit(this);
			}
			oninit.add(id);
		}
		
		
		if(controller instanceof OnChange) {
			log.trace("onChange: {}",controller.getClass().getSimpleName());
			((OnChange)controller).onChage(this);
		}
		
		getPanel().showContent( controller.getPanel() );
		
	}

	@Override
	public JPanelApp getPanel() {
		return panel;
	}

	@Override
	public void addMenuItem(MenuItem menuItem) {
		
		getPanel().addMenuItem(menuItem.getGroup(), menuItem.getText(), menuItem.getIcon(), (item) -> show(item, menuItem.getController()));
	}
	
	public void addController(Controller Controller) {
		
		if(Controller instanceof OnInit) {
			log.trace("onInit: {}",Controller.getClass().getSimpleName());
			((OnInit)Controller).onInit(this);
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
	
	
	public void updateAll(Class lookAndFeel) {
		log.trace("{} updateAll",this.getClass().getSimpleName());
		getPanel().updateAll(lookAndFeel);
	}

}

