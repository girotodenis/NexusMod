package br.com.dsg.legui.controller;

import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseClickEvent.MouseClickAction;
import org.liquidengine.legui.listener.EventListener;

public class ActionClickListener implements EventListener<MouseClickEvent> {

	private static Logger LOG = Logger.getLogger(AbstractController.class);
	
	private AbstractController<?> controller;
	private Component source;
	private Action action;
	
	private Vector4f color;
	private Vector4f colorSelect;
	private List<EventListener<MouseClickEvent>> eventListeners;
	
	public ActionClickListener(AbstractController<?> controller, Component source, Action action) {
		this.source = source;
		this.action = action;
		this.controller = controller;
	}
	
	@Override
	public void process(MouseClickEvent event) {
			if(event.getAction().equals(MouseClickAction.CLICK)) {
				this.colorSelect = source.getPressedStyle().getBackground().getColor();
				this.color = source.getStyle().getBackground().getColor();
				bloquearComponente();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							LOG.debug(String.format("Executando click %s ", source.toString() ));
							action.executar(event);
						} catch (Exception e) {
							controller.handlerException(e);
						} finally {
							habilitarComponente();
						}
					}

				});
			}
	}

	public void habilitarComponente() {
		source.setEnabled(true);
		source.setPressed(false);
		source.getStyle().getBackground().setColor(ActionClickListener.this.color);
		for (EventListener<MouseClickEvent> eventListener : ActionClickListener.this.eventListeners) {
			source.getListenerMap().addListener(MouseClickEvent.class, eventListener);
		}
	}
	
	public void bloquearComponente() {
		source.setEnabled(false);
		source.setPressed(true);
		source.getStyle().getBackground().setColor(this.colorSelect);
		ActionClickListener.this.eventListeners =  source.getListenerMap().getListeners(MouseClickEvent.class);
		source.getListenerMap().removeAllListeners(MouseClickEvent.class);
	}

}
