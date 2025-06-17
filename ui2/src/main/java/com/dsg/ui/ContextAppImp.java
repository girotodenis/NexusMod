package com.dsg.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsg.nexusmod.ui.AbstractEventListener;
import com.dsg.nexusmod.ui.ContextApp;

public class ContextAppImp implements ContextApp {
	
	private Map<String, List<AbstractEventListener<?>>> eventListeners = new HashMap<String, List<AbstractEventListener<?>>>();
	
	/**
	 * Aciona o <code>AbstractEventListener</code> relacionado ao
	 * <code>AbstractEvent</code> para que o <code>listener</code> trate o evento.
	 * 
	 * @param event referÃªncia do evento gerado
	 */
	public <T> void fireEvent(T event) {
		fireEvent(event.getClass().getName(), event);
	}
	
	/**
	 * Aciona o <code>AbstractEventListener</code> relacionado ao
	 * <code>AbstractEvent</code> para que o <code>listener</code> trate o evento.
	 * 
	 * @param event referÃªncia do evento gerado
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> void fireEvent(String event, T date ) {
		System.out.println("Firing event: " + event);
		System.out.println("Firing event: this " + this+ event);
		if (eventListeners.get(event) != null) {
			for (AbstractEventListener eventListener : eventListeners.get(event)) {
				System.out.println("Firing eventListener: " + eventListener);
				eventListener.handleEvent(date);
			}
		}
	}
	
	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo
	 * do <code>evento</code>.
	 * 
	 * @param eventClass    tipo do evento
	 * @param eventListener tratador (<code>listener</code>) do evento
	 */
	public <T> void registerEvent(Class<T> eventClass, AbstractEventListener<T> eventListener) {
		registerEvent(eventClass.getName(), eventListener);
	}
	
	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo
	 * do <code>evento</code>.
	 * 
	 * @param eventClass    tipo do evento
	 * @param eventListener tratador (<code>listener</code>) do evento
	 */
	public <T> void registerEvent(String event, AbstractEventListener<T> eventListener) {
		System.out.println("Registering event: this " + this);
		System.out.println("Registering event: " + event);
		List<AbstractEventListener<?>> listenersForEvent = eventListeners.get(event);
		if (listenersForEvent == null) {
			listenersForEvent = new ArrayList<AbstractEventListener<?>>();
		}
		listenersForEvent.add(eventListener);
		eventListeners.put(event, listenersForEvent);
	}

	@Override
	public boolean contens(String event) {
		return eventListeners.get(event)!=null && !eventListeners.get(event).isEmpty();
	}
	

}
